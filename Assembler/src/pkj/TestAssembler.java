package pkj;

import java.io.File;

public class TestAssembler {

	public static void main(String[] args) {
		Assembler assembler = new Assembler();
		
		long startTime,endTime;
		startTime = System.currentTimeMillis();
		assembler.assemble(new File("src/prog6.txt"));
		endTime = System.currentTimeMillis();
		
		System.out.println("Total time = " +(endTime-startTime) + " ms");
	}

}
