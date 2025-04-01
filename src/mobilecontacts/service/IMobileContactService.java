package mobilecontacts.service;

import mobilecontacts.Exceptions.ContactNotFoundException;
import mobilecontacts.Exceptions.PhoneNumberAlreadyExistsException;
import mobilecontacts.dto.MobileContactInsertDTO;
import mobilecontacts.dto.MobileContactUpdateDTO;
import mobilecontacts.model.MobileContact;

import java.util.List;

public interface IMobileContactService {

    MobileContact insertMobileContact(MobileContactInsertDTO dto)
            throws PhoneNumberAlreadyExistsException;

    MobileContact updateMobileContact(MobileContactUpdateDTO dto)
            throws PhoneNumberAlreadyExistsException, ContactNotFoundException;

    void deleteContactById(Long id) throws ContactNotFoundException;

    MobileContact getContactById(Long id) throws ContactNotFoundException;

    List<MobileContact> getAllContacts();

    MobileContact getContactByPhoneNumber(String phoneNumber) throws ContactNotFoundException;

    void deleteContactByPhoneNumber(String phoneNumber) throws ContactNotFoundException;
}
