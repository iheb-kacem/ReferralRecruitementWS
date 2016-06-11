package com.referalrecruitement.domain;

public class Superuser extends Personal {

	@Override
	public String toString() {
		return "Superuser [getPersonalID()=" + getPersonalID()
				+ ", getFirstName()=" + getFirstName() + ", getLastName()="
				+ getLastName() + ", getEmail()=" + getEmail()
				+ ", getPassword()=" + getPassword()
				+ ", getConfirmePassword()=" + getConfirmePassword()
				+ ", getEntreprise()=" + getEntreprise() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	
}
