package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.IOException;

// 3-33 BufferedReader를 전달하는 콜백 인터페이스
public interface BufferedReaderCallback {
	Integer doSomethingWithReader(BufferedReader br) throws IOException;
}
