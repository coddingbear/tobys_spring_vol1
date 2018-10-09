package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// 3-31 처음 만든 Calculator 클래스 코드
public class Calculator {
	public Integer calcSum(String filepath) throws IOException {
		// lineReadTemplate()을 사용하도록 수정한 calSum(), calcMultiply() 메소드
		LineCallback<Integer> sumCallback =
			new LineCallback<Integer>() {				
				@Override
				public Integer doSomethingWithLine(String line, Integer value) {
					return value + Integer.valueOf(line);
				}
			};
		return lineReadTemplate(filepath, sumCallback, 0);
		/*
		BufferedReaderCallback sumCallback =
				 new BufferedReaderCallback() {
					@Override
					public Integer doSomethingWithReader(BufferedReader br) throws IOException {
						Integer sum = 0;
						String line = null;
						while((line = br.readLine()) != null) { // 마지막 라인까지 한 줄씩 읽어가면서 숫자를 더한다.
							sum += Integer.valueOf(line);
						}
						return sum;
					}
				};
		return fileReadTemplate(filepath, sumCallback);
		*/
	}
	
	// 3-37 곱을 계산하는 콜백을 가진 calcMultiply() 메소드
	public Integer calcMultiply(String filepath) throws IOException {
		LineCallback<Integer> multiplyCallback = 
			new LineCallback<Integer>() {
				@Override
				public Integer doSomethingWithLine(String line, Integer value) {
					return value * Integer.valueOf(line);
				}
			};
		return lineReadTemplate(filepath, multiplyCallback, 1);
		/*
		BufferedReaderCallback multiplyCallback =
				new BufferedReaderCallback() {
					@Override
					public Integer doSomethingWithReader(BufferedReader br) throws IOException {
						Integer multiply = 1;
						String line = null;
						while((line = br.readLine()) != null) { // 마지막 라인까지 한 줄씩 읽어가면서 숫자를 더한다.
							multiply *= Integer.valueOf(line);
						}
						return multiply;
					}
				};
		return fileReadTemplate(filepath, multiplyCallback);
		*/
	}
	
	// 3-43 문자열 연결 기능 콜백을 이용해 만든 concatenate() 메소드
	public String concatenate(String filepath) throws IOException {
		LineCallback<String> concatenateCallback =
			new LineCallback<String>() {
				@Override
				public String doSomethingWithLine(String line, String value) {
					// TODO Auto-generated method stub
					return value + line;
				}
			};
		return lineReadTemplate(filepath, concatenateCallback, "");
	}
	
	// 3-39 LineCallback을 사용하는 템플릿
	public <T> T lineReadTemplate(String filepath, LineCallback<T> callback, T initVal) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			T res = initVal;
			String line = null;
			while((line = br.readLine()) != null) {
				res = callback.doSomethingWithLine(line, res);
			}
			return res;
		} catch(IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if(br != null) { 
				try { br.close(); } 
				catch(IOException e) { System.out.println(e.getMessage()); }
			}
		}
	}
	
	// 3-34 BufferedReaderCallback을 사용하는 템플릿 메소드
	public Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath)); // 한 줄씩 읽기 편하게 BufferedReader로 파일을 가져온다.
			int ret = callback.doSomethingWithReader(br);
			return ret;
		} catch(IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if(br != null) { 
				try { br.close(); } 
				catch(IOException e) { System.out.println(e.getMessage()); }
			}
		}
	}
}
