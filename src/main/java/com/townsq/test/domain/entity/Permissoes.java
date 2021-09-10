package com.townsq.test.domain.entity;

import com.townsq.test.domain.model.StatusPermissao;

public class Permissoes implements Comparable<Permissoes> {

	private String permissao;

	private String status;

	public String getPermissao() {
		return permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return " (" + permissao + ", " + status + ")";
	}

	@Override
	public int compareTo(Permissoes o) {

		int i = 0;
		if (this.getStatus() == null) {
			return -1;
		} else if (this.getStatus().toUpperCase().equals(StatusPermissao.ESCRITA.toString())
				&& !o.getStatus().toUpperCase().equalsIgnoreCase(StatusPermissao.ESCRITA.toString())) {
			i = 1;
		} else if (this.getStatus().toUpperCase().equals(StatusPermissao.NENHUMA.toString())
				&& !o.getStatus().toUpperCase().equals(StatusPermissao.NENHUMA.toString())) {
			i = -1;
		} else if (this.getStatus().equals(o.getStatus())) {

			i = 0;
		} else if (this.getStatus().toUpperCase().equals(StatusPermissao.LEITURA.toString())
				&& !o.getStatus().toUpperCase().equals(StatusPermissao.ESCRITA.toString())) {
			i = 1;
		}
		return i;
	}

}
