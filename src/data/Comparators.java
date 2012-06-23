package data;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/***
 * ����4���Ƚ���
 * 
 * @author lqy
 * 
 */
public class Comparators {

	/**
	 * ������ƴ������
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
	 * ѧ�ŵ�����
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

	// ����ƴ������Ƚ���
	public static Comparator<Student> pInc = t.new PinyinComp();
	// ����ƴ������Ƚ���
	public static Comparator<Student> pDec = t.new PinyinComp() {
		@Override
		public int compare(Student o1, Student o2) {
			return -super.compare(o1, o2);
		}
	};
	// ѧ������Ƚ���
	public static Comparator<Student> nInc = t.new NumComp();
	// ѧ������Ƚ���
	public static Comparator<Student> nDec = t.new NumComp() {
		@Override
		public int compare(Student o1, Student o2) {
			return -super.compare(o1, o2);
		}
	};

}
