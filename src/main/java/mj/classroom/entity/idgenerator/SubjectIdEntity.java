package mj.classroom.entity.idgenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subject_id_generator")
public class SubjectIdEntity {

	@Id
	@Column(name = "generated_key")
	private String key;
	
	@Column(name = "generated_value")
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
