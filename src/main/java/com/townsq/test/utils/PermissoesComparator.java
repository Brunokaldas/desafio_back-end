package com.townsq.test.utils;

import java.util.Comparator;

import com.townsq.test.domain.entity.Permissoes;

public class PermissoesComparator implements Comparator<Permissoes> {

	@Override
	public int compare(Permissoes o1, Permissoes o2) {
		
		return o1.compareTo(o2);
	}

}
