package mobilecontacts.controller;

import mobilecontacts.Exceptions.ContactNotFoundException;
import mobilecontacts.Exceptions.PhoneNumberAlreadyExistsException;
import mobilecontacts.core.serializer.Serializer;
import mobilecontacts.dao.IMobileContactDAO;
import mobilecontacts.dao.MobileContactDAOImpl;
import mobilecontacts.dto.MobileContactInsertDTO;
import mobilecontacts.dto.MobileContactReadOnlyDTO;
import mobilecontacts.dto.MobileContactUpdateDTO;
import mobilecontacts.mapper.Mapper;
import mobilecontacts.model.MobileContact;
import mobilecontacts.service.IMobileContactService;
import mobilecontacts.service.MobileContactServiceImpl;
import mobilecontacts.validation.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

public class MobileContactController {

    private final IMobileContactDAO dao = new MobileContactDAOImpl();
    private final IMobileContactService service = new MobileContactServiceImpl(dao);

    public String insertContact(MobileContactInsertDTO insertDTO) {
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;
        try {
            // Validate input data
            String errorVector = ValidationUtil.validateDTO(insertDTO);
            if (!errorVector.isEmpty()) {
                return "Error.\n" + "Validation error\n" + errorVector;
            }

            // If validation is ok, insert contact
            mobileContact = service.insertMobileContact(insertDTO);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "OK\n" + Serializer.serializeDTO(readOnlyDTO);
        } catch (PhoneNumberAlreadyExistsException e) {
            return "Error.\n" + e.getMessage() + "\n";
        }
    }

    public String updateContact(MobileContactUpdateDTO updateDTO)  {
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;

        try {
            // Validate input data
            String errorVector = ValidationUtil.validateDTO(updateDTO);
            if (!errorVector.isEmpty()) {
                return "Error.\n" + "Validation error\n" + errorVector;
            }

            // If validation is ok, insert contact
            mobileContact = service.updateMobileContact(updateDTO);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "OK\n" + Serializer.serializeDTO(readOnlyDTO);
        } catch (PhoneNumberAlreadyExistsException e) {
            return "Error.\n" + e.getMessage() + "\n";
        } catch (ContactNotFoundException e) {
            return "Error.\n" + e.getMessage() + "\n";
        }
    }

    public String deleteContactById(Long id) {
        try {
            service.deleteContactById(id);
            return "OK\n Η επαφή διαγράφηκε";
        } catch (ContactNotFoundException e) {
            return "Error.\n" + "Λάθος κατά τη διαγραφή. Η επαφή δεν βρέθηκε";
        }
    }

    public String getContactById(Long id) {
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;
        try {
            mobileContact = service.getContactById(id);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "OK\n" + Serializer.serializeDTO(readOnlyDTO);
        } catch (ContactNotFoundException e) {
            return "Error.\n" + "Η επαφή δεν βρέθηκε \n";
        }
    }

    public List<String> getAllContacts() {
        List<MobileContact> contacts;
        List<String> serializedList = new ArrayList<>();
        MobileContactReadOnlyDTO readOnlyDTO;
        String serialized;

        contacts = service.getAllContacts();

        for (MobileContact contact : contacts) {
            readOnlyDTO = Mapper.mapMobileContactToDTO(contact);
            serialized = Serializer.serializeDTO(readOnlyDTO);
            serializedList.add(serialized);
        }

        return serializedList;
    }

    public String getContactByPhoneNumber(String phoneNumber) {
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;
        try {
            mobileContact = service.getContactByPhoneNumber(phoneNumber);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "OK\n" + Serializer.serializeDTO(readOnlyDTO);
        } catch (ContactNotFoundException e) {
            return "Error.\n" + "Η επαφή δεν βρέθηκε \n";
        }
    }

    public String deleteContactByPhoneNumber(String phoneNumber) {
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;
        try {
            mobileContact = service.getContactByPhoneNumber(phoneNumber);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            service.deleteContactByPhoneNumber(phoneNumber);

            return "OK\n Η επαφή διαγράφηκε" + Serializer.serializeDTO(readOnlyDTO);
        } catch (ContactNotFoundException e) {
            return "Error.\n" + "Λάθος κατά τη διαγραφή. Η επαφή δεν βρέθηκε";
        }
    }
}