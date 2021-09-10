package com.townsq.test.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class Grupos {

	private String tipo;
	private String idCondominio;
	private List<Permissoes> permissoes = new ArrayList<>();

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(String textoAux) {
		this.idCondominio = textoAux;
	}

	public List<Permissoes> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissoes> permissoes) {
		this.permissoes = permissoes;
	}

	@Override
	public String toString() {
		return "Grupos [tipo=" + tipo + ", idCondominio=" + idCondominio + ", permissoes=" + permissoes + "]";
	}
	
	

}
