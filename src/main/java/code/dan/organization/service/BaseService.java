package code.dan.organization.service;

public interface BaseService <I, O>{

    O execute(I input);

}
