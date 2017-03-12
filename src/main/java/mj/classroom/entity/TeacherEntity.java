package mj.classroom.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "teacher")
public class TeacherEntity {
	
	@Column(name = "teacher_phone_no")
	private Long phone;
	
	@Column(name = "teacher_email_id")
	private String email;
	
	@Column(name = "teacher_teaching_charge")
	private Double fees;
	
	@EmbeddedId
	private TeachingEntity teaches;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "subject_id")
	private Set<SubjectEntity> subjects;
	
	public TeachingEntity getTeaches() {
		return teaches;
	}

	public void setTeaches(TeachingEntity teaches) {
		this.teaches = teaches;
	}
	
	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getFees() {
		return fees;
	}

	public void setFees(Double fees) {
		this.fees = fees;
	}
	
	
	
}
