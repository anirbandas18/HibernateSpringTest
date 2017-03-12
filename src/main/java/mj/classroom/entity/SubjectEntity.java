package mj.classroom.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "subject")
public class SubjectEntity {

	@Id
	@Column(name = "subject_id")
	@TableGenerator(name = "subject_id_generator", table = "subject_id_generator", pkColumnName = "generated_key", valueColumnName = "generated_value", pkColumnValue = "subject_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "subject_id_generator")
	private int id;

	@Column(name = "subject_name")
	private String name;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "subject_parent")
	private SubjectEntity parent;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parent", cascade = CascadeType.ALL)
	private List<SubjectEntity> children;

	@Column(name = "subject_duration")
	private int duration;

	@Column(name = "subject_credits")
	private int credits;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SubjectEntity getParent() {
		return parent;
	}

	public void setParent(SubjectEntity parent) {
		this.parent = parent;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public List<SubjectEntity> getChildren() {
		return children;
	}

	public void setChildren(List<SubjectEntity> children) {
		this.children = children;
	}

	public SubjectEntity(int id) {
		super();
		this.id = id;
	}

	public SubjectEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubjectEntity other = (SubjectEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
