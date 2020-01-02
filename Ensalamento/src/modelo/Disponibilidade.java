package modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import cfg.enums.StatusModelo;

@Entity
public final class Disponibilidade implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long id;
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	public Date data;	
	
	public boolean segM;  public boolean segT;  public boolean segN; 
	public boolean terM;  public boolean terT;  public boolean terN; 
	public boolean quaM;  public boolean quaT;  public boolean quaN; 
	public boolean quiM;  public boolean quiT;  public boolean quiN; 
	public boolean sexM;  public boolean sexT;  public boolean sexN; 
	public boolean sabM;  public boolean sabT;  public boolean sabN; 
	public boolean domM;  public boolean domT;  public boolean domN;
 
	@Override
	public String toString() {
		return "Disponibilidade [id=" + id + "; data=" + data + "; segM=" + segM
				+ "; segT=" + segT + "; segN=" + segN + "; terM=" + terM + "; terT=" + terT + "; terN=" + terN
				+ "; quaM=" + quaM + "; quaT=" + quaT + "; quaN=" + quaN + "; quiM=" + quiM + "; quiT=" + quiT
				+ "; quiN=" + quiN + "; sexM=" + sexM + "; sexT=" + sexT + "; sexN=" + sexN + "; sabM=" + sabM
				+ "; sabT=" + sabT + "; sabN=" + sabN + "; domM=" + domM + "; domT=" + domT + "; domN=" + domN + "]";
	}
	
	public void setValoresIniciais(){
		 segM = false;   segT = false;   segN = false; 
		 terM = false;   terT = false;   terN = false; 
		 quaM = false;   quaT = false;   quaN = false; 
		 quiM = false;   quiT = false;   quiN = false; 
		 sexM = false;   sexT = false;   sexN = false; 
		 sabM = false;   sabT = false;   sabN = false; 
		 domM = false;   domT = false;   domN = false;
	}
	
	
}
