package rsquest.kr.tpsw.api.publica;

import java.io.File;

public class StringAPI {
	
	//1.0

	public static String mergeArgs(String[] args, int start, int end, char c) {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < end; i++) {
			sb.append(args[i]).append(c);
		}
		return sb.toString().trim();
	}

	public static String mergeArgs(String[] args, int start) {
		return mergeArgs(args, start, args.length, ' ');
	}

	public static String mergeArgsUnder(String[] args, int start) {
		return mergeArgs(args, start, args.length, '_');
	}

	public static boolean contains(String s, char c) {
		return s.indexOf(c) > -1;
	}

	public static int getCharCount(String s, char c) {
		int count = 0;
		for (char cc : s.toCharArray()) {
			if (cc == c) {
				count++;
			}
		}
		return count;
	}

	public static String getFileNameWithoutExtension(File file) {
		if (!file.isDirectory()) {
			String name = file.getName();
			int index = name.lastIndexOf('.');
			return name.substring(0, index);
		} else {
			System.out.println(file.getAbsolutePath());
			//잘못된 파일, 아마 이 메서드 잘못됨
			return null;
		}
	}

}
