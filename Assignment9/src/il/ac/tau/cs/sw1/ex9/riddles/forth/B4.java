package il.ac.tau.cs.sw1.ex9.riddles.forth;

import java.util.Iterator;

public class B4 implements Iterator<String> {
	
	private String[] strings;
	private int k;
	
	public B4(String[] strings, int k){
		String[] tmp = new String[strings.length * k];
		for (int i = 0; i < strings.length; i++){
			for (int j = 0; j < k; j++){
				tmp[i+strings.length*j] = strings[i];
			}
		}
		this.strings = tmp;
		this.k = 0;
	}

	@Override
	public boolean hasNext() {
		return k < strings.length;
	}

	@Override
	public String next() {
		k++;
		return strings[k-1];
	}
}
