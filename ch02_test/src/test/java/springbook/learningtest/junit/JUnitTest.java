package springbook.learningtest.junit;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * 2-24 JUnit 테스트 오브젝트 생성에 대한 학습 테스트
 * 2-25 개선한 JUnit 테스트 오브젝트 생성에 대한 학습 테스트
 */
public class JUnitTest {
	// static JUnitTest testObject;
	static Set<JUnitTest> testObjects = new HashSet<>();
	
	@Test public void test1() {
		//assertThat(this,  is(not(sameInstance(testObject))));
		//testObject = this;
		assertThat(testObjects,  not(hasItem(this)));
		testObjects.add(this);
	}
	
	@Test public void test2() {
		assertThat(testObjects,  not(hasItem(this)));
		testObjects.add(this);
	}
	
	@Test public void test3() {
		assertThat(testObjects,  not(hasItem(this)));
		testObjects.add(this);
	}
}
