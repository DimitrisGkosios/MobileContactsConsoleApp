package mobilecontacts.mapper;

import mobilecontacts.dto.MobileContactInsertDTO;
import mobilecontacts.dto.MobileContactReadOnlyDTO;
import mobilecontacts.dto.MobileContactUpdateDTO;
import mobilecontacts.model.MobileContact;

public class Mapper {

    /**
     * No instances of this class should be available.
     */
    private Mapper() {

    }

    public static MobileContact mapInsertDTOToContact(MobileContactInsertDTO dto) {
        return new MobileContact(null, dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber());
    }

    public static MobileContact mapUpdateDTOToContact(MobileContactUpdateDTO dto) {
        return new MobileContact(dto.getId(), dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber());
    }

    public static MobileContactReadOnlyDTO mapMobileContactToDTO(MobileContact mobileContact) {
        return new MobileContactReadOnlyDTO(mobileContact.getId(), mobileContact.getFirstname(),
                mobileContact.getLastname(), mobileContact.getPhoneNumber());
    }
}
