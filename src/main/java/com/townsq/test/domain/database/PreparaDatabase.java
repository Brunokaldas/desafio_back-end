package com.townsq.test.domain.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.townsq.test.domain.entity.Grupos;
import com.townsq.test.domain.entity.Permissoes;
import com.townsq.test.domain.entity.Usuarios;

@Component
public class PreparaDatabase {
	private List<Usuarios> usuariosBanco = new ArrayList<Usuarios>();
	private List<Grupos> gruposBanco = new ArrayList<Grupos>();
	private static final String CAMINHO_DATA = System.getProperty("user.dir") + System.getProperty("file.separator")
			+ "\\src\\main\\resources\\database\\data.txt";

	private enum PermissoesExistentes {
		_1_RESERVAS("Reservas"), _2_ENTREGAS("Entregas"), _3_USUARIOS("Usuarios");

		private String descricao;

		PermissoesExistentes(String descricao) {
			this.descricao = descricao;
		}	
		
		   public String getTitulo() {
		        return this.name();
		    }

	}

	public void preparaDatabase() throws FileNotFoundException {

		File file = new File(CAMINHO_DATA);

		Scanner scan = new Scanner(file);

		String texto = "";
		String[] textoSeparado = {};
		while (scan.hasNextLine()) {

			texto = scan.nextLine();
			textoSeparado = texto.split(";");

			switch (textoSeparado[0]) {
			case "Usuario":

				usuariosBanco.add(preparaUsuario(textoSeparado[1], textoSeparado[2]));

				break;
			case "Grupo":

				gruposBanco.add(preparaGrupo(textoSeparado[1], textoSeparado[2], textoSeparado[3]));

			default:
				break;
			}
		}

		scan.close();

		for (Usuarios usuario : usuariosBanco) {

			for (Grupos grupo : usuario.getGrupo()) {
				for (Grupos grupoDatabase : gruposBanco) {
					if (grupoDatabase.getTipo().equals(grupo.getTipo())
							&& grupoDatabase.getIdCondominio().equals(grupo.getIdCondominio())) {
						grupo.setPermissoes(grupoDatabase.getPermissoes());
					}
				}

			}
		}

	}

	private Grupos preparaGrupo(String grupo, String idCondominio, String permissoes) {

		Grupos grupos = new Grupos();
		grupos.setTipo(grupo);
		grupos.setIdCondominio(idCondominio);

		String permissaoSemBarra = permissoes.substring(permissoes.indexOf("[") + 1, permissoes.indexOf("]"));
		String[] permissaoAux = permissaoSemBarra.split("\\),");
		for (int i = 0; i < permissaoAux.length; i++) {

			Permissoes permissao = new Permissoes();
			permissaoAux[i] = permissaoAux[i].replaceAll("[\\p{Ps}\\p{Pe}]", "");
			String[] permissaoAux2 = permissaoAux[i].split(",");
			permissao.setPermissao(permissaoAux2[0]);
			permissao.setStatus(permissaoAux2[1]);

			grupos.getPermissoes().add(permissao);
		}
		return grupos;
	}

	private Usuarios preparaUsuario(String email, String grupo) {
		Usuarios usuario = new Usuarios();

		usuario.setEmail(email);
		String grupoString = grupo;
		grupoString = grupoString.substring(grupoString.indexOf("[") + 1, grupoString.indexOf("]"));
		String[] grupoAux = grupoString.split("\\),");
		for (int i = 0; i < grupoAux.length; i++) {
			Grupos grupos = new Grupos();
			grupoAux[i] = grupoAux[i].replaceAll("[\\p{Ps}\\p{Pe}]", "");
			String[] grupoAux2 = grupoAux[i].split(",");
			grupos.setTipo(grupoAux2[0]);
			grupos.setIdCondominio(grupoAux2[1]);

			usuario.getGrupo().add(grupos);
		}
		return usuario;
	}

	public List<Permissoes> listarPermissoesPorEmail(String email) throws FileNotFoundException {
		List<Usuarios> usuarios = usuariosBanco;
		List<Permissoes> permissoesEncontradas = new ArrayList<Permissoes>();
		Map<String, List<Grupos>> gruposPorCondominio = new HashMap<String, List<Grupos>>();
		for (Usuarios usuario : usuarios) {
			if (usuario.getEmail().equals(email)) {
				for (Grupos grupo : usuario.getGrupo()) {
					if (gruposPorCondominio.containsKey(grupo.getIdCondominio())) {
						gruposPorCondominio.get(grupo.getIdCondominio()).add(grupo);
					} else {
						gruposPorCondominio.put(grupo.getIdCondominio(), new ArrayList<>(Arrays.asList(grupo)));
					}
				}

				break;
			}
		}

		for (List<Grupos> grupos : gruposPorCondominio.values()) {

			Map<String, Permissoes> mapeamentoPermissoes = new TreeMap<>();

			for (Grupos grupo : grupos) {

				for (PermissoesExistentes p : PermissoesExistentes.values()) {

					for (Permissoes permissoes : grupo.getPermissoes()) {
						if (permissoes.getPermissao().equals(p.descricao)) {

							if (mapeamentoPermissoes.containsKey(p.getTitulo())) {

								mapeamentoPermissoes.put(p.getTitulo(),
										(mapeamentoPermissoes.get(p.getTitulo()).compareTo(permissoes) == 1
												? mapeamentoPermissoes.get(p.getTitulo())
												: permissoes));
							} else {
								mapeamentoPermissoes.put(p.getTitulo(), permissoes);
							}

						}
					}

				}

			}

			permissoesEncontradas.addAll(mapeamentoPermissoes.values());

		}
		return permissoesEncontradas;
	}

}
