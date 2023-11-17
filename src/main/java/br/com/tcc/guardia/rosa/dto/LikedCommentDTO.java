package br.com.tcc.guardia.rosa.dto;

public class LikedCommentDTO {
	
	private boolean liked;
	private Long curtidas;
	
	public LikedCommentDTO(boolean liked, Long curtidas) {
		this.liked = liked;
		this.curtidas = curtidas;
	}
	public LikedCommentDTO() {
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
