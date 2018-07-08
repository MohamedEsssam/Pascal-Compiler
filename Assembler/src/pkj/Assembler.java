package pkj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.channels.NetworkChannel;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;

public class Assembler {
	private int locCounter;
	private HashMap<String , Integer> symTable;
	private static int programLength;
	private int startingAddress;
	private InstructionMatcher iMatcher;
	private String programName, recordAdr = null;
	private int recordSize = 0, preRecordSize = 0;
	private StringBuilder output;
	private HashMap<String , String> instructionSet;
	private ArrayList<StringBuilder> writee = new ArrayList<StringBuilder>();


	public Assembler() {
		iMatcher = new InstructionMatcher();
		loadInstructionSet(new File("src/instructionSet.txt"));
		output = new StringBuilder();
	}

	public void assemble(File inputProgram) {
		File intermediate = pass1(inputProgram);
		pass2(intermediate);
		try (Formatter formatter = new Formatter(new File("src/programOut.txt"));) {
			output.append("E -" + String.format("%06X", startingAddress));
			formatter.format(output.toString()).close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(0);
		}
	}

	private File pass1(File program) {
		symTable = new HashMap<String, Integer>();
		File intermediateFile = new File("src/intermediate.txt");
		File symTableFile = new File("src/symTable.txt");

		try (Scanner scanner = new Scanner(program);
				PrintWriter writer = new PrintWriter(intermediateFile);
				PrintWriter symtableWriter = new PrintWriter(symTableFile);) {

			String instruction = scanner.nextLine();
			iMatcher.setInstruction(instruction);

			if (iMatcher.getOpcode().equals("START")) {
				programName = iMatcher.getLabel().substring(0, 6);
				locCounter = Integer.parseInt(iMatcher.getOperand(), 16);
				startingAddress = Integer.parseInt(iMatcher.getOperand(), 16);
				writer.append(String.format("%06X", locCounter) + "\t" + instruction + "\n");
				instruction = scanner.nextLine();
				iMatcher.setInstruction(instruction);
			} else {
				locCounter = startingAddress = 0;
			}

			int instructionLoc;
			do {
				instructionLoc = locCounter;
				if (!iMatcher.isComment()) {
					if (iMatcher.getLabel() != null) {
						if (symTable.containsKey(iMatcher.getLabel()))
							throw new Exception("duplicate symbol");
						else
							symTable.put(iMatcher.getLabel(), locCounter);
					}
					if (instructionSet.containsKey(iMatcher.getOpcode())) {
						locCounter += 3;
					} else if (iMatcher.getOpcode().equals("WORD")) {
						locCounter += 3;
					} else if (iMatcher.getOpcode().equals("RESW")) {
						locCounter += 3 * Integer.parseInt(iMatcher.getOperand());
					} else if (iMatcher.getOpcode().equals("RESB")) {
						locCounter += Integer.parseInt(iMatcher.getOperand());
					} else if (iMatcher.getOpcode().equals("BYTE")) {
						if (iMatcher.getOperand().charAt(0) == 'X')
							locCounter += 1;
						else if (iMatcher.getOperand().charAt(0) == 'C')
							locCounter += (iMatcher.getOperand().length() - 3);
					} else {
						throw new Exception("invalid operation code: " + iMatcher.getOpcode());
					}

				}
				writer.append(String.format("%06X", instructionLoc) + "\t" + instruction + "\n");
				instruction = scanner.nextLine();
				iMatcher.setInstruction(instruction);

			} while (!iMatcher.getOpcode().equals("END"));
			programLength = locCounter - startingAddress;
			System.out.println("Program length = " + Integer.toHexString(programLength) + "H");
			writer.append(String.format("%06X", programLength) + "\t" + instruction + "\n");
			for (String key : symTable.keySet()) {
				symtableWriter.append(key + "  " + String.format("%06X", symTable.get(key)));
				symtableWriter.println();
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		return intermediateFile;
	}

	private void loadInstructionSet(File f) {
		instructionSet = new HashMap<String, String>();
		try (Scanner reader = new Scanner(f)) {
			while (reader.hasNextLine()) {
				instructionSet.put(reader.next(), reader.next());
			}

		} catch (FileNotFoundException e) {
			System.out.println("instruction set file not found");
			System.exit(0);
		}
	}

	private void pass2(File intermediate) {
		output.append("H-" + programName + "-" + String.format("%06X", startingAddress) + "-"
				+ String.format("%06X", programLength) + "\n");

		String opCode, location, operand = null;
		boolean indexed, comment = false;

		try (Scanner scanner = new Scanner(intermediate)) {
			while (scanner.hasNext()) {
				location = scanner.next();
				String instruction = scanner.nextLine();
				iMatcher.setInstruction(instruction);

				operand = iMatcher.getOperand();
				opCode = iMatcher.getOpcode();
				indexed = iMatcher.isIndexed();
				comment = iMatcher.isComment();

				StringBuilder inst = new StringBuilder();
				if (!comment) {
					if (!opCode.equals("END")) {
						if (!opCode.equals("START")) {

							if (instructionSet.containsKey(opCode) && symTable.containsKey(operand)) {
								int op = symTable.get(operand);
								inst.append(instructionSet.get(opCode));
								if (opCode.equals("CLEAR")) {
									inst.append("00");
									writee.add(inst);
									recordSize += 3;
									preRecordSize = 3;
									if (recordAdr == null)
										recordAdr = location;
									return;
								}

								if (indexed) {
									op += 32768;
									String ch = (String) Integer.toHexString(op).toUpperCase();
									inst.append(ch);
									writee.add(inst);
									recordSize += 3;
									preRecordSize = 3;
									if (recordAdr == null)
										recordAdr = location;
								} else {
									// if not using indexed
									inst.append(String.format("%04X", op));
									writee.add(inst);
									recordSize += 3;
									preRecordSize = 3;
									if (recordAdr == null)
										recordAdr = location;
								}

							}

							else if (opCode.equals("WORD")) {
								// define constant word
								if (operand.charAt(operand.length() - 1) == 'H') {
									String zeros = "000000";
									zeros = zeros.substring(0, 6 - (operand.length() - 1));
									inst.append(zeros);
									inst.append(operand.substring(0, operand.length() - 1));
								} else {
									int op = Integer.parseInt(operand);
									inst.append(String.format("%06X", op));
								}
								writee.add(inst);
								recordSize += 3;
								preRecordSize = 3;
								if (recordAdr == null)
									recordAdr = location;
							} else if (opCode.equals("RSUB")) {
								inst.append(instructionSet.get(opCode));
								inst.append("0000");
								writee.add(inst);
								recordSize += 3;
								preRecordSize = 3;
								if (recordAdr == null)
									recordAdr = location;
							} else if (opCode.equals("RESB") || opCode.equals("RESW")) {
								if (writee.size() != 0) {
									writeRecord(writee);
									writee.clear();
									recordAdr = null;
									recordSize = 0;
								}
							} else if (opCode.equals("BYTE")) {
								// define constant byte
								if (operand.charAt(0) == 'X') {
									recordSize += 1;
									preRecordSize = 1;
									inst.append(operand.substring(2, operand.length() - 1));
									writee.add(inst);
								} else if (operand.charAt(0) == 'C') {
									recordSize += operand.length() - 3;
									preRecordSize = operand.length() - 3;
									for (int i = 2; i < operand.length() - 1; i++) {
										int ch = operand.charAt(i);
										inst.append(Integer.toHexString(ch));
									}
									writee.add(inst);
									if (recordAdr == null)
										recordAdr = location;
								}

							} else {
								throw new Exception("Error: " + instruction);
							}

						}
					} else {
						if (writee.size() != 0) {
							writeRecord(writee);
							writee.clear();
							recordAdr = null;
							recordSize = 0;
							break;
						}
					}
				}
				if (recordSize == 30) {
					writeRecord(writee);
					writee.clear();
					recordAdr = null;
					recordSize = 0;
				} else if (recordSize > 30) {
					StringBuilder lastObjectCode = writee.remove(writee.size() - 1);
					recordSize -= preRecordSize;
					writeRecord(writee);
					writee.clear();
					recordAdr = location;
					writee.add(lastObjectCode);
					recordSize = preRecordSize;
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			System.exit(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

	}

	private void writeRecord(ArrayList<StringBuilder> arr) {
		String count = String.format("%02X", recordSize);

		output.append("T-" + String.format("%06X", Integer.parseInt(recordAdr, 16)) + "-" + count + "-");
		for (int i = 0; i < arr.size(); i++) {
			if (i == arr.size() - 1)
				output.append(arr.get(i).toString());
			else
				output.append(arr.get(i).toString() + "-");
		}
		output.append("\n");

	}

}
