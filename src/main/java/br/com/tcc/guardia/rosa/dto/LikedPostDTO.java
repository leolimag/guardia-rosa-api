package br.com.tcc.guardia.rosa.dto;

public class LikedPostDTO {
	
	private boolean liked;
	private Long curtidas;
	
	public LikedPostDTO(boolean liked, Long curtidas) {
		this.liked = liked;
		this.curtidas = curtidas;
	}
	public LikedPostDTO() {
	}
	public boolean isLiked() {
		return liked;
	}
	public void setLiked(boolean liked) {
		this.liked = liked;
	}
	public Long getCurtidas() {
		return curtidas;
	}
	public void setCurtidas(Long curtidas) {
		this.curtidas = curtidas;
	}
	
}
