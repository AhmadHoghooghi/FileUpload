package com.rhotiz.upload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;

public class CreateFileUtil {
	private static byte[] createBufferOfRandomDigits(int size_byte) {
		byte[] result = new byte[size_byte];
		Random rand = new Random();
		rand.nextBytes(result);
		return result;
	}

	static File createFileOfSize_InMB(int totalFileSizeInMB) throws IOException {
		int bufferSize_M = 4;
		if (totalFileSizeInMB < bufferSize_M) {
			throw new RuntimeException("file size must be bigger than buffer");
		}
		long iter = totalFileSizeInMB / bufferSize_M;
		Path file = Paths.get("testFile.dat");

		for (long i = 0; i < iter; i++) {
			byte[] buffer = createBufferOfRandomDigits(bufferSize_M * 1024 * 1024);
			Files.write(file, buffer, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
		}
		return file.toFile();
	}
}
