/* Copyright 2009 1fb.net Financial Services
 *
 * This document may not be reproduced, distributed or used in any
 * manner whatsoever without the expressed written permission of
 * 1st Financial Bank USA.
 */
package com.onefbusa.ccms.web.ddoconverter;

import org.apache.commons.lang.StringUtils;

import com.onefbusa.ccms.web.Constants;
import com.onefbusa.ccms.web.domaindata.InactivateReason;
import com.onefbusa.ccms.web.domaindata.contactmechanism.Telephone;
import com.onefbusa.ccms.web.domaindata.contactmechanism.TelephoneInfo;
import com.onefbusa.ccms.web.domaindata.nonsolicitation.NonSolicitStatus;
import com.onefbusa.ccms.web.form.PhoneContactForm;
import com.onefbusa.ccms.web.presentationdata.PhoneNumber;
import com.onefbusa.ccms.web.utilities.FormatUtil;
import com.onefbusa.ccms.web.utilities.timeanddate.TimeAndDate;
import com.onefbusa.ccmsddo.AddPhoneDDO;
import com.onefbusa.ccmsddo.ConsentDDO;
import com.onefbusa.ccmsddo.PhoneNumberDDO;

/**
 * convert class for converting a PhoneContactForm or TelephoneInfo to AddPhoneDDO
 * Since the convert logic of converting from phoneContactForm to AddPhoneDDO is complex,
 * separate the logic out of BusinessLayerBWImpl.java
 * 
 * @author harry.li
 */
public class AddPhoneDDOConverter {

    private static final long serialVersionUID = 3273830107379474604L;
    
    private static final String FDR_HOME = "home";
    
    private static final String FDR_WORK = "work";
    
    private static final String FDR_NEITHER = "neither";
    
    public static final int FDR_HOME_FLAG = 1;
    
    public static final int FDR_WORK_FLAG = 2;
    
    public static final int FDR_NEITHER_FLAG = 3;
    
    public static final int IS_NOT_FDR_NUMBER = 0;
    
    public static final int IS_FDR_HOME_NUMBER = 1;
    
    public static final int IS_FDR_WORK_NUMBER = 2;
    
    public static final int IS_BOTH_FDR_HOME_AND_WORK_NUMBER = 3;
    
    /**
     * Convert a phoneContactForm to AddPhoneDDO
     * 
     * @param phoneContactForm
     * @return
     */
    public static AddPhoneDDO convert(PhoneContactForm phoneContactForm) {
        TelephoneInfo telephoneInfo = phoneContactForm.getTelephoneInfoDDO();
        
        PhoneNumber phonenumber = phoneContactForm.getPhoneNumber();
        
        AddPhoneDDO addPhoneDDO = new AddPhoneDDO();
        
        // set phone number
        PhoneNumberDDO phoneNumberDDO = new PhoneNumberDDO();
        
        phoneNumberDDO.setAreaCode(phonenumber.getAreaCode().trim());
        phoneNumberDDO.setPhoneNumber(phonenumber.getPrefix().trim()+phonenumber.getSuffix().trim());
        phoneNumberDDO.setExtension(phonenumber.getExt().trim());
        
        addPhoneDDO.setPhoneNumber(phoneNumberDDO);
        
        // set restricted use flag
        //for active telephone, set it with user input; for inactive telephone, set it with previous value
        if(phoneContactForm.getStatus().equals(Constants.CONTACT_STATUS_ACTIVE)){
        	addPhoneDDO.setIsRestricted(phoneContactForm.getRestrictedUseFlag().equals(Constants.PHONE_NUMBER_RESTRICT_USE));
        }else if(telephoneInfo==null){
        	//for add inactivated phone number
        	addPhoneDDO.setIsRestricted(false);
        }else{
        	//for edit inactivated phone number
        	addPhoneDDO.setIsRestricted(!telephoneInfo.getTelephone().getAutomatedUseAllowed());
        }

        // set owner type
        try {
        	addPhoneDDO.setOwnerTypeId(Integer.valueOf(phoneContactForm.getOwner()));
        } catch(NumberFormatException e) {
            throw new RuntimeException("parse OwnerTypeId failed");
        }
        
        
        // set phone type
        try {
        	addPhoneDDO.setPhoneTypeId(Integer.valueOf(phoneContactForm.getType()));
        } catch(NumberFormatException e) {
            throw new RuntimeException("parse PhoneTypeId failed");
        }
        
        // set first name
        if(phoneContactForm.getFirstName() != null){
        	addPhoneDDO.setFirstName(StringUtils.upperCase(phoneContactForm.getFirstName()));
        }
        
        // set last name
        if(phoneContactForm.getLastName() != null){
        	addPhoneDDO.setLastName(StringUtils.upperCase(phoneContactForm.getLastName()));
        }
        
        if(Constants.isPecFunctionEnabled()) {
            // determine PEC related information based on PhoneContactForm
            determinePECFromPhoneContactForm(phoneContactForm, addPhoneDDO);
        }

        /*
         * The followings are optional fields 
         */
        
        if (!phoneContactForm.getStatus().equals(Constants.CONTACT_STATUS_ACTIVE)) {
        	try {
        		addPhoneDDO.setInactivateReasonId(Integer.valueOf(phoneContactForm.getInactivationReason()));
        	} catch(NumberFormatException e) {
                throw new RuntimeException("parse InactivateReasonId failed");
            }
        	
        	// set reactivate date
            if (phoneContactForm.getReactivationDate().getIsBlank()) {
                addPhoneDDO.setReactivationDate(Constants.NEVER_REACTIVATION_DATE);
            } else {
                addPhoneDDO.setReactivationDate(FormatUtil.formatTimeAndDate(phoneContactForm.getReactivationDate().getTimeAndDateValue(), FormatUtil.DEFAULT_STRING_FORMAT));
            }
            
            if (InactivateReason.getInstance(null).getRequestDeliveryFilter().get(addPhoneDDO.getInactivateReasonId()).equals(Boolean.FALSE)) {
            	addPhoneDDO.setInactivateRequesterTypeId(Integer.valueOf(Constants.UNKNOWN_REQUESTED_BY));
            	addPhoneDDO.setInactivateDeliveryTypeId(Integer.valueOf(Constants.UNKNOWN_REQUEST_TYPE));
            }else{
            	
            	// set inactivation requested by
            	if (!StringUtils.isEmpty(phoneContactForm.getInactivationRequestedBy())) {
            		try {
            			addPhoneDDO.setInactivateRequesterTypeId(Integer.valueOf(phoneContactForm.getInactivationRequestedBy()));
            		} catch(NumberFormatException e) {
                        throw new RuntimeException("parse InactivateRequesterTypeId failed");
                    }
            	} else {
                	addPhoneDDO.setInactivateRequesterTypeId(Integer.valueOf(Constants.UNKNOWN_REQUESTED_BY));
            	}
            
            	if (!StringUtils.isEmpty(phoneContactForm.getRequestType())) {
            		try {
            			addPhoneDDO.setInactivateDeliveryTypeId(Integer.valueOf(phoneContactForm.getRequestType()));
            		} catch(NumberFormatException e) {
                        throw new RuntimeException("parse InactivateDeliveryTypeId failed");
                    }
            	} else {
                	addPhoneDDO.setInactivateDeliveryTypeId(Integer.valueOf(Constants.UNKNOWN_REQUEST_TYPE));
            	}
            }
            
        }
        
        // set update fdr flag
        if (!phoneContactForm.getStatus().equals(Constants.CONTACT_STATUS_ACTIVE)) {
            addPhoneDDO.setUpdateFdrCode(FDR_NEITHER_FLAG);
        } else if (phoneContactForm.getUpdateFDRFlag().equals(FDR_HOME)) {
            addPhoneDDO.setUpdateFdrCode(FDR_HOME_FLAG);
        } else if (phoneContactForm.getUpdateFDRFlag().equals(FDR_WORK)) {
            addPhoneDDO.setUpdateFdrCode(FDR_WORK_FLAG);
        } else if (phoneContactForm.getUpdateFDRFlag().equals(FDR_NEITHER)) {
            addPhoneDDO.setUpdateFdrCode(FDR_NEITHER_FLAG);
        }
        
        // set memo
        if (phoneContactForm.getMemo() != null
                && phoneContactForm.getMemo().length() != 0) {
            addPhoneDDO.setMemoText(phoneContactForm.getMemo());
        }
        // set New Verify Date check box flag
        if(phoneContactForm.getNewVerifiedDateflag().equalsIgnoreCase(Constants.NEW_VERIFIED_DATE_CHECK_BOX_ON)){
        	addPhoneDDO.setNewVerifyDate(new TimeAndDate().toString());        	
        }else if(phoneContactForm.getLastVerifiedDate().getIsBlank()){
        	addPhoneDDO.setNewVerifyDate(null);
        }else{
        	addPhoneDDO.setNewVerifyDate(phoneContactForm.getLastVerifiedDate().getTimeAndDateValue().toString());
        }
        
        return addPhoneDDO;
    }
    
    /**
     * Determine PEC related information based on phoneContactForm
     * 
     * @param phoneContactForm
     * @param addPhoneDDO
     */
    private static void determinePECFromPhoneContactForm(PhoneContactForm phoneContactForm, AddPhoneDDO addPhoneDDO) {
        
        TelephoneInfo telephoneInfo = phoneContactForm.getTelephoneInfoDDO();
        
        // if the telephone is cell phone, set PEC status for it.
        if (Constants.PHONE_TYPE_CELL.equals(phoneContactForm.getType())) {
            if (Constants.UNKNOWN_PEC.equals(phoneContactForm.getNewConsentStatusId())) {
                // FE has not set PEC status
                
                if(telephoneInfo != null && telephoneInfo.getConsentDDO() != null){
                    // telephoneInfo contains PEC information, that means this phone
                    // number has original PEC information and user doesn't select new
                    // PEC status, reuse the old status as requirement.
                    
                    addPhoneDDO.setConsentInfo(telephoneInfo.getConsentDDO());
                }else{
                    // telephoneInfo doesn't contain PEC information, that means this phone
                    // number doesn't have original PEC information, set to null
                    
                    ConsentDDO consentDDO = new ConsentDDO(Constants.PEC_NS, null, null, null);
                    addPhoneDDO.setConsentInfo(consentDDO);
                }
            } else {
                // FE has already set PEC status
                // use the PEC information set by user
                
                Integer pecMethod = Constants.UNKNOWN_PEC_METHOD
                        .equals(phoneContactForm.getNewPecMethodId()) ? null
                        : phoneContactForm.getNewPecMethodId();
                ConsentDDO consentDDO = new ConsentDDO(phoneContactForm.getNewConsentStatusId(), pecMethod, null, null);
                addPhoneDDO.setConsentInfo(consentDDO);
            }
            // For CCMS 3.30 TT item 474 cease communication name is changed to cease communication cm
        } else if (Constants.PEC_NO.equals(phoneContactForm.getNewConsentStatusId()) &&
                (Constants.INACTIVATION_REASON_CEASE_COMMUNICATION_CM.equals(phoneContactForm.getInactivationReason()) ||
                Constants.INACTIVATION_REASON_DNC_ALL_NUMBERS.equals(phoneContactForm.getInactivationReason()))) {
            //For CCMS 3.10 CR1 REQ00435, if the phone type is non-Cell, and inactivation reason is Cease Communication or
            //DNC ALL NUMBERS, the PEC will be set to NO for all CELL phone of this person.
            ConsentDDO consentDDO = new ConsentDDO(Constants.PEC_NO, phoneContactForm.getNewPecMethodId(), null, null);
            addPhoneDDO.setConsentInfo(consentDDO);
        }
    }
    
    /**
     * Convert telephoneInfo to AddPhoneDDO
     * 
     * @param telephoneInfo
     * @return
     */
    public static AddPhoneDDO convert(TelephoneInfo telephoneInfo) {
        AddPhoneDDO addPhoneDDO = new AddPhoneDDO();
        
        Telephone telephone = telephoneInfo.getTelephone();
        
        // set phone number
        PhoneNumberDDO phoneNumberDDO = new PhoneNumberDDO();
        String phoneNumber = telephone.getPhoneNumber();
        phoneNumberDDO.setPhoneNumber(phoneNumber);
        if (telephone.getExtension() != null
                && telephone.getExtension().length() != 0) {
            phoneNumberDDO.setExtension(telephone.getExtension().trim());
        } else {
            // Do not set ext number value
        }
        phoneNumberDDO.setAreaCode(telephone.getAreaCode());
        addPhoneDDO.setPhoneNumber(phoneNumberDDO);
        
        // set restricted use flag
        addPhoneDDO.setIsRestricted(!telephone.isAutomatedUseAllowed());
        
        // set owner type
        addPhoneDDO.setOwnerTypeId(telephone.getOwnerTypeId());
        
        // set phone type
        addPhoneDDO.setPhoneTypeId(telephone.getTelephoneTypeId());
        
        // if the telephone is cell phone, set PEC status for it.
        addPhoneDDO.setConsentInfo(telephoneInfo.getConsentDDO());
        
        addPhoneDDO.setFirstName(telephone.getFirstName());
        
        addPhoneDDO.setLastName(telephone.getLastName());
        
        addPhoneDDO.setSuffix(telephone.getSuffix());
        
        /*
         * The followings are optional fields 
         */
        
        // set inactive reason
        NonSolicitStatus oldStatus = telephoneInfo.getStatus();
        if (oldStatus != null) {
            addPhoneDDO.setInactivateReasonId(oldStatus.getNonSolicitationTypeId());
        
            // set reactivate date
            if (oldStatus.getThruDate() == null) {
                addPhoneDDO.setReactivationDate(Constants.NEVER_REACTIVATION_DATE);
            } else {
                addPhoneDDO.setReactivationDate(FormatUtil
                        .formatTimeAndDate(oldStatus.getThruDate(), FormatUtil.DEFAULT_STRING_FORMAT));
            }
            
            // set inactivation requested by
            if (oldStatus.getRequestSourceTypeId() != Constants.NOT_INITIALIZED_INT
                    && oldStatus.getRequestSourceTypeId() != Integer
                            .valueOf(Constants.UNKNOWN_REQUESTED_BY)) {
                addPhoneDDO.setInactivateRequesterTypeId(oldStatus
                        .getRequestSourceTypeId());
            } else {
                addPhoneDDO.setInactivateRequesterTypeId(Integer
                        .valueOf(Constants.UNKNOWN_REQUESTED_BY));
            }

            // set request type
            if (oldStatus.getDeliveryMethodTypeId() != Constants.NOT_INITIALIZED_INT
                    && oldStatus.getDeliveryMethodTypeId() != Integer
                            .valueOf(Constants.UNKNOWN_REQUEST_TYPE)) {
                addPhoneDDO.setInactivateDeliveryTypeId(oldStatus
                        .getDeliveryMethodTypeId());
            } else {
                addPhoneDDO.setInactivateDeliveryTypeId(Integer
                        .valueOf(Constants.UNKNOWN_REQUEST_TYPE));
            }
        }
        
        return addPhoneDDO;
        
    }
    
}
