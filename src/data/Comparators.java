package data;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/***
 * 存了4个比较器
 * 
 * @author lqy
 * 
 */
public class Comparators {

	/**
	 * 姓名的拼音正序
	 * 
	 * @author lqy
	 * 
	 */
	protected class PinyinComp implements Comparator<Student> {
		protected Collator c = Collator.getInstance(Locale.CHINESE);

		@Override
		public int compare(Student o1, Student o2) {
			return c.compare(o1.name, o2.name);
		}
	}

	/**
	 * 学号的正序
	 * 
	 * @author lqy
	 * 
	 */
	protected class NumComp implements Comparator<Student> {
		@Override
		public int compare(Student o1, Student o2) {
			return o1.no.compareTo(o2.no);
		}
	}

	protected static Comparators t = new Comparators();

	// 姓名拼音正序比较器
	public static Comparator<Student> pInc = t.new PinyinComp();
	// 姓名拼音逆序比较器
	public static Comparator<Student> pDec = t.new PinyinComp() {
		@Override
		public int compare(Student o1, Student o2) {
			return -super.compare(o1, o2);
		}
	};
	// 学号正序比较器
	public static Comparator<Student> nInc = t.new NumComp();
	// 学号逆序比较器
	public static Comparator<Student> nDec = t.new NumComp() {
		@Override
		public int compare(Student o1, Student o2) {
			return -super.compare(o1, o2);
		}
	};

}
