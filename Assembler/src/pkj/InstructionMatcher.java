package pkj;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstructionMatcher {
	// private String regex =
	// "\\A[\\s]{0,}((?<label>[a-zA-Z0-9]{1,})[\\s]{1}){0,1}[\\s]{0,}(?<opcode>[A-Z]{1,})[\\s]{1,}(?<operand>[a-zA-Z0-9]{1,})(?:[\\s]{0,}[,][\\s]{0,}(?<x>[x])){0,}";

	private final Pattern COMMENT_PATTERN = Pattern.compile("^[\\s]{0,}[.][\\w\\s]{0,}");
	private final Pattern LABEL_OPCODE_OPERAND_PATTERN = Pattern.compile(
			"^[\\s]{0,}(?<label>[a-zA-Z]{1}[\\w]{0,})[\\s]{1,}(?<opcode>[A-Z]{1,6})[\\s]{1,}(?<operand>(?:[a-zA-Z]{1}[\\w]{0,})|(?:[\\d]{1,6})|(?:[cC]['][\\w]{1,}['])|(?:[xX]['][\\dA-F]{1,}[']))[\\s]{0,}$");
	private final Pattern LABEL_OPCODE_OPERAND_X_PATTERN = Pattern.compile(
			"^[\\s]{0,}(?<label>[a-zA-Z]{1}[\\w]{0,})[\\s]{1,}(?<opcode>[A-Z]{1,6})[\\s]{1,}(?<operand>[a-zA-Z]{1}[\\w]{0,})[\\s]{0,}[,][\\s]{0,}[xX][\\s]{0,}$");
	private final Pattern OPCODE_OPERAND_PATTERN = Pattern.compile(
			"^[\\s]{0,}(?<opcode>[A-Z]{1,6})[\\s]{1,}(?<operand>(?:[a-zA-Z]{1}[\\w]{0,})|(?:[\\d]{1,6}))[\\s]{0,}$");
	private final Pattern OPCODE_OPERAND_X_PATTERN = Pattern.compile(
			"^[\\s]{0,}(?<opcode>[A-Z]{1,6})[\\s]{1,}(?<operand>[a-zA-Z]{1}[\\w]{0,})[\\s]{0,}[,][\\s]{0,}[xX][\\s]{0,}$");
	private final Pattern OPCODE_PATTERN = Pattern.compile("^[\\s]{0,}(?<opcode>[A-Z]{1,6})[\\s]{0,}$");
	private String label;
	private String opcode;
	private String operand;
	private boolean comment;
	private boolean indexed;
	private List<Pattern> patterns;

	public InstructionMatcher() {
		patterns = new ArrayList<Pattern>();
		patterns.add(COMMENT_PATTERN);
		patterns.add(LABEL_OPCODE_OPERAND_PATTERN);
		patterns.add(LABEL_OPCODE_OPERAND_X_PATTERN);
		patterns.add(OPCODE_OPERAND_PATTERN);
		patterns.add(OPCODE_OPERAND_X_PATTERN);
		patterns.add(OPCODE_PATTERN);
	}

	public void setInstruction(String instruction) throws Exception {
		boolean matched = false;
		for (int i = 0; i < patterns.size(); i++) {
			Pattern p = patterns.get(i);
			Matcher matcher = p.matcher(instruction);
			if (matcher.matches()) {
				switch (i) {
				case 0:
					label = null;
					opcode = "ay7aga";
					operand = null;
					indexed = false;
					comment = true;
					break;
				case 1:
					label = matcher.group("label");
					opcode = matcher.group("opcode");
					operand = matcher.group("operand");
					indexed = false;
					comment = false;
					break;
				case 2:
					label = matcher.group("label");
					opcode = matcher.group("opcode");
					operand = matcher.group("operand");
					indexed = true;
					comment = false;
					break;
				case 3:
					label = null;
					opcode = matcher.group("opcode");
					operand = matcher.group("operand");
					indexed = false;
					comment = false;
					break;
				case 4:
					label = null;
					opcode = matcher.group("opcode");
					operand = matcher.group("operand");
					indexed = true;
					comment = false;
					break;
				case 5:
					label = null;
					opcode = matcher.group("opcode");
					operand = null;
					indexed = false;
					comment = false;
					break;
				}
				matched = true;
				break;
			}
		}

		if (!matched) {
			throw new Exception("instruction not valid: " + instruction);
		}

	}

	public String getLabel() {
		return label;
	}

	public String getOpcode() {
		return opcode;
	}

	public String getOperand() {
		return operand;
	}

	public boolean isComment() {
		return comment;
	}

	public boolean isIndexed() {
		return indexed;
	}

}
