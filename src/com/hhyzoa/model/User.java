package com.hhyzoa.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


/**
 * 
 * @author Bill
 *
 */
@Entity(name="t_user")
public class User {
	
	private Integer id;
	private String userName;
	private String userPwd;
	
	private Set<Message> messages; //与消息一对多
	
	@OneToMany(mappedBy="user",
			cascade={CascadeType.REMOVE}
		//fetch=FetchType.EAGER
	)
	public Set<Message> getMessages() {
		return messages;
	}
	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="user_name")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name="user_pwd")
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	
	
	

}
