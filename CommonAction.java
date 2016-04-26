/* Copyright 2004 1fb.net Financial Services
 *
 * This document may not be reproduced, distributed or used in any
 * manner whatsoever without the expressed written permission of
 * 1st Financial Bank USA.
 */
package com.onefbusa.ccms.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.onefbusa.ccms.web.BusinessLayerInterface;
import com.onefbusa.ccms.web.Constants;
import com.onefbusa.ccms.web.form.LoginForm;
import com.onefbusa.ccms.web.form.PhoneContactForm;
import com.onefbusa.ccms.web.form.SortInfoForm;
import com.onefbusa.ccms.web.presentationdata.AccountSummary;
import com.onefbusa.ccms.web.presentationdata.FDRAgent;
import com.onefbusa.ccms.web.presentationdata.PhoneContactList;
import com.onefbusa.ccms.web.utilities.FormatUtil;
import com.onefbusa.ccms.web.utilities.Util;
import com.onefbusa.ccms.web.utilities.timeanddate.TimeAndDate;
import com.onefbusa.ccmsddo.GetPhoneContactsOutputDDO;
 

/**
 * Code that may be used by more than one action is factored here. NOTE: This
 * class is not an action, per se (it has no execute() method).
 */
public class CommonAction {
   //~ Methods -----------------------------------------------------------------

   /**
    * Ensure availability of object expected to be available (ie, non-null).
    * Useful for ensuring a value is available if expected in some servlet
    * context (usually session).  Also useful to ensure a value is supplied in
    * a method call (eg, that a form is supplied to Action.execute()). If the
    * object is not available, an exception will be thrown.  Presumably, this
    * will be caught by a top level exception handler.  
    *
    * @param messageResources resources locator; used to
    *        access resource bundle to get localized message
    * @param request the request being processed; used to support the simulated
    *        error feature, which needs to reference session and application
    *        context
    * @param o the object which we are validating, to ensure it is not null
    * @param errMsgKey key to entry in resource bundle associated with the
    *        error condition in case the object is not available; used to
    *        define the message text which is passed to the exception
    *        constructor
    *
    * @throws RuntimeException
    */
   public static void validateAvailability(MessageResources messageResources,
      HttpServletRequest request, Object o, String errMsgKey) {

      if(o == null) {
         String message = messageResources.getMessage(errMsgKey);

         throw new RuntimeException(message);
      }
   }

   /**
    * Ensure availability of string expected to be nonblank (ie, not
    * zero-length or all whitespace).  Useful for ensuring a value is
    * available if expected in some servlet context (usually session).  If the
    * string is null or blank, an exception will be thrown.  Presumably, this
    * will be caught by a top level exception handler.  
    *
    * @param messageResources messageResources locator; used to
    *        access resource bundle to get localized message
    * @param request the request being processed; used to support the simulated
    *        error feature, which needs to reference session and application
    *        context
    * @param s the string we are validating, to ensure it is non-blank
    * @param errMsgKey key to entry in resource bundle associated with the
    *        error condition in case the string is null or blank; used to
    *        define the message text which is passed to the exception
    *        constructor
    *
    * @throws RuntimeException
    */
   public static void validateNonblank(MessageResources messageResources,
      HttpServletRequest request, String s, String errMsgKey) {

      if((s == null) || (s.trim().length() == 0)) {
         String message = messageResources.getMessage(errMsgKey);

         throw new RuntimeException(message);
      }
   }

   /**
    * Determine if an exception should be simulated in an action by checking
    * for existence of a hash table found in session or application scope,
    * identified by Constants.DEBUG_SIMULATE_ACTION_EXCEPTION_KEY, and if
    * found, existence of at least one key in the hash.  The first key found
    * will be used to simulate an exception.  Additional keys will be ignored
    * since only one exception can be simulated at a time.  Also, remove the
    * key if found before simulating an exception.  If the last key is removed
    * from the hash, then remove the hash from scope as well.
    *
    * @param request The request being processed.
    *
    * @throws ServletException
    * @throws IOException
    */
   public static void possiblySimulateActionException(
      HttpServletRequest request) throws ServletException, IOException {
      possiblySimulateException(request,
         Constants.DEBUG_SIMULATE_ACTION_EXCEPTION_KEY);
   }

   /**
    * Determine if an exception should be simulated while rendering a JSP by
    * checking for existence of a hash table found in session or application
    * scope, identified by Constants.DEBUG_SIMULATE_JSP_EXCEPTION_KEY, and if
    * found, existence of at least one key in the hash.  The first key found
    * will be used to simulate an exception.  Additional keys will be ignored
    * since only one exception can be simulated at a time.  Also, remove the
    * key if found before simulating an exception.  If the last key is removed
    * from the hash, then remove the hash from scope as well.
    *
    * @param request The request being processed.
    *
    * @throws ServletException
    * @throws IOException
    */
   public static void possiblySimulateJspException(HttpServletRequest request)
      throws ServletException, IOException {
      possiblySimulateException(request,
         Constants.DEBUG_SIMULATE_JSP_EXCEPTION_KEY);
   }

   /**
    * Determine if an exception should be simulated by checking for existence
    * of a specified hash table found in session or application scope and, if
    * found, existence of at least one key in the hash.  The first key found
    * will be used to simulate an exception.  Additional keys will be ignored
    * since only one exception can be simulated at a time.  Also, remove the
    * key if found before simulating an exception.  If the last key is removed
    * from the hash, then remove the hash from scope as well.
    *
    * @param request The request being processed.
    * @param hashID The name of the hash table used to keep track of simulated
    *        exceptions.  This is the key used to attach the hash table to
    *        session or application scope.
    *
    * @throws ServletException in case of a servlet exception
    * @throws IOException in case of an IO exception
    * @exception java.lang.RuntimeException
    * @exception java.lang.NullPointerException
    * @exception java.io.IOException
    * @exception javax.servlet.ServletException
    * @exception oracle.toplink.exceptions.TopLinkException
    * @exception org.springframework.dao.DataAccessException
    */
   public static void possiblySimulateException(HttpServletRequest request,
      String hashID) throws ServletException, IOException {
      HttpSession session = request.getSession();

      ServletContext servletContext = session.getServletContext();


      Hashtable hash = (Hashtable) session.getAttribute(hashID);

      boolean sessionScope = (hash != null);


      if(!sessionScope) {
         hash = (Hashtable) servletContext.getAttribute(hashID);
      }


      String exceptionClassName = null;


      if(hash != null) {
         // identify exception if there is one, and remove it from the hash
         java.util.Enumeration keys = hash.keys();


         if(keys.hasMoreElements()) {
            exceptionClassName = (String) keys.nextElement();

            hash.remove(exceptionClassName);
         }


         // remove hash if there are no more elements in it
         if(hash.size() == 0) {
            if(sessionScope) {
               session.removeAttribute(hashID);
            } else {
               servletContext.removeAttribute(hashID);
            }
         }
      }


      // simulate the exception already!
      if(exceptionClassName != null) {
         if(exceptionClassName.equals("java.lang.RuntimeException")) {
            throw new java.lang.RuntimeException("Simulated exception here");
         } else if(exceptionClassName.equals("java.lang.NullPointerException")) {
            throw new java.lang.NullPointerException("Simulated exception here");
         } else if(exceptionClassName.equals("java.io.IOException")) {
            throw new java.io.IOException("Simulated exception here");
         } else if(exceptionClassName.equals("javax.servlet.ServletException")) {
            throw new javax.servlet.ServletException("Simulated exception here");
         } else if(exceptionClassName.equals(
               "org.springframework.dao.DataAccessException")) {
            throw new org.springframework.dao.DataAccessException(
               "Simulated exception here") {
                  ;
               }
            ;
         } else {
            throw new java.lang.RuntimeException("Simulated exception here.  "
               + "Could not simulate exception of type " + exceptionClassName
               + ", because it is unrecognized.  Simulating RuntimeException instead");
         }


         /*
            try {
              Class dynamicClass = Class.forName(exceptionClassName);
              Class[] constructorArgumentTypes = { String.class };
              java.lang.reflect.Constructor classConstructor
                = dynamicClass.getConstructor(constructorArgumentTypes);
              Object[] constructorArgs = { new String("Simulated exception here") };
              throw (Exception) classConstructor.newInstance(constructorArgs);
            } catch (ClassNotFoundException e) {
              throw new Exception("Error simulating exception of type " + exceptionClassName);
            } catch (Exception e) {
              throw new Exception("Error simulating exception of type " + exceptionClassName);
            }
          */
      }
   }

   /**
    * Determine if an operation should be logged based on the presence or
    * absence of a scoped attribute identified by the debugParamName argument.
    *
    * @param request The request being processed.
    * @param debugParamName identifier of debug parameter used as key of
    *        session or servlet context attribute
    *
    * @return true if debug parameter is found in session or application scope
    *         with a value of "enable"
    */
   public static boolean shouldLog(HttpServletRequest request,
      String debugParamName) {
      HttpSession session = request.getSession();

      String debugParamValue = (String) session.getAttribute(debugParamName);


      if(debugParamValue == null) {
         ServletContext servletContext = session.getServletContext();

         debugParamValue = (String) servletContext.getAttribute(debugParamName);
      }


      return (debugParamValue != null) && debugParamValue.equals("enable");
   }

   /**
    * Log request parameters.
    *
    * @param request The request being processed.
    * @param logger object used to log messages
    * @param context An arbitrary value representing the context from which
    *        this method is invoked
    */
   public static void logRequestParameters(HttpServletRequest request,
      Logger logger, String context) {
      Enumeration parameters = request.getParameterNames();

      logger.info( context + " :: request parameters follow");


      while(parameters.hasMoreElements()) {
         String name = (String) parameters.nextElement();

         String value = (String) request.getParameter(name);

         logger.info( context + " ::   " + name + " = [" + value + "]");


         String[] values = request.getParameterValues(name);


         if((values != null) && (values.length > 1)) {
            logger.info( context + " ::   " + name
               + ": all values follow");


            for(int i = 0; i < values.length; i++) {
              logger.info(  
                  context + " ::     " + name + " = [" + values[i] + "]");
            }
         }
      }
   }

   /**
    * Log request attributes.
    *
    * @param request The request being processed.
    * @param logger object used to log messages
    * @param context An arbitrary value representing the context from which
    *        this method is invoked
    */
   public static void logRequestAttributes(HttpServletRequest request,
      Logger logger, String context) {
      logger.info( context + " :: request attributes follow");


      Enumeration attributes = request.getAttributeNames();


      while(attributes.hasMoreElements()) {
         String key = (String) attributes.nextElement();

         Object value = request.getAttribute(key);

         logger.info( context + " ::   " + key + " = [" + value + "]");
      }
   }

   /**
    * Log session attributes.
    *
    * @param request The request being processed.
    * @param logger object used to log messages
    * @param context An arbitrary value representing the context from which
    *        this method is invoked
    */
   public static void logSessionAttributes(HttpServletRequest request,
      Logger logger, String context) {
      logger.info( context + " :: session attributes follow");


      HttpSession session = request.getSession();

      Enumeration attributes = session.getAttributeNames();


      while(attributes.hasMoreElements()) {
         String key = (String) attributes.nextElement();

         Object value = session.getAttribute(key);

         logger.info( context + " ::   " + key + " = [" + value + "]");
      }
   }

   /**
    * Log application attributes.
    *
    * @param request The request being processed.
    * @param logger object used to log messages
    * @param context An arbitrary value representing the context from which
    *        this method is invoked
    */
   public static void logApplicationAttributes(HttpServletRequest request,
      Logger logger, String context) {
      logger.info( context + " :: application attributes follow");


      ServletContext application = request.getSession().getServletContext();

      Enumeration attributes = request.getSession().getServletContext()
                                      .getAttributeNames();


      while(attributes.hasMoreElements()) {
         String key = (String) attributes.nextElement();

         Object value = application.getAttribute(key);

         logger.info( context + " ::   " + key + " = [" + value + "]");
      }
   }

   /**
    * Log a message.
    *
    * @param logger the Log4J logger used to log a message
    * @param msg the message to log
    */
   public static void logMessage(Logger logger, String msg) {
      //// Whoever so boldly dares to uncomment the System.out.println below
      //// shall place the comment back before checkin or suffer a million
      //// untold discomforts through a curse placed on all who read this! -pbs
      // System.out.println(msg);
      if(logger.isDebugEnabled()) {
         logger.debug(msg);
      }
   }

   /**
    * Generate debugging output of request information immediately after
    * entering an action, if this debugging feature has been enabled through
    * DevAdmin page.
    *
    * @param request The HTTP Request we are processing.
    * @param logger the Log4J logger used to log a message
    */
   public static void maybeLogRequestInfoOnActionEntry(
      HttpServletRequest request, Logger logger) {
      if(shouldLog(request, Constants.DEBUG_HTTP_INFO_AFTER_ENTER_ACTION_KEY)) {
         logRequestParameters(request, logger, "ENTER execute()");

         logRequestAttributes(request, logger, "ENTER execute()");

         logSessionAttributes(request, logger, "ENTER execute()");


         // logApplicationAttributes(request, logger, "ENTER execute()");
      }
   }

   /**
    * Generate debugging output of request information immediately before
    * exiting an action, if this debugging feature has been enabled through
    * DevAdmin page.
    *
    * @param request The HTTP Request we are processing.
    * @param logger the Log4J logger used to log a message
    */
   public static void maybeLogRequestInfoOnActionExit(
      HttpServletRequest request, Logger logger) {
      if(shouldLog(request, Constants.DEBUG_HTTP_INFO_BEFORE_EXIT_ACTION_KEY)) {
         logRequestAttributes(request, logger, "EXIT execute()");

         logSessionAttributes(request, logger, "EXIT execute()");
      }
   }

   /**
    * Get ActionErrors object stored in session.  If none exists, create a new,
    * empty one and store it in the session.  Errors are stored in the session
    * to allow survival, through a redirect, for display purposes.
    *
    * @param request The HTTP Request we are processing.
    *
    * @return Struts object used to hold error messages
    */
   public static ActionErrors getErrorsForDisplay(HttpServletRequest request) {
      // NOTE: the following two statements are equivalent
      //    1.  saveErrors(request, errors);
      //    2.  request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errors);
      HttpSession session = request.getSession();

      ActionErrors errors = (ActionErrors) session.getAttribute(org.apache.struts.Globals.ERROR_KEY);


      if(errors == null) {
         errors = new ActionErrors();

         session.setAttribute(org.apache.struts.Globals.ERROR_KEY, errors);
      }


      return errors;
   }

   /**
    * Restore Struts errors by moving from session to request scope.  Errors
    * are stored in the session to allow survival, through a redirect, for
    * display purposes.
    *
    * @param request The HTTP Request we are processing.
    */
   public static void restoreErrorsForDisplay(HttpServletRequest request) {
      HttpSession session = request.getSession();

      ActionErrors errors = (ActionErrors) session.getAttribute(org.apache.struts.Globals.ERROR_KEY);


      if(errors != null) {
         if(errors.size() > 0) {
            request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errors);
         }


         session.removeAttribute(org.apache.struts.Globals.ERROR_KEY);
      }
   }

   /**
    * Determine if there are errors for display, stored in the session.  This
    * refers to errors managed using getErrorsForDisplay() and
    * restoreErrorsForDisplay().
    *
    * @param request The HTTP Request we are processing.
    *
    * @return true if there are errors for display; false otherwise
    */
   public static boolean existErrorsForDisplay(HttpServletRequest request) {
      HttpSession session = request.getSession();

      ActionErrors errors = (ActionErrors) session.getAttribute(org.apache.struts.Globals.ERROR_KEY);

      boolean result = ((errors != null) && (errors.size() > 0));


      return result;
   }

   /**
    * Get message list stored in session.  If none exists, create a new, empty
    * one and store it in the session.  Messages are stored in the session to
    * allow survival, through a redirect, for display purposes.
    *
    * @param request The HTTP Request we are processing.
    *
    * @return array list containing zero or more String messages
    */
   public static ArrayList getMessagesForDisplay(HttpServletRequest request) {
      HttpSession session = request.getSession();

      ArrayList messages = (ArrayList) session.getAttribute(Constants.MESSAGE_LIST_KEY);


      if(messages == null) {
         messages = new ArrayList();

         session.setAttribute(Constants.MESSAGE_LIST_KEY, messages);
      }


      return messages;
   }

   /**
    * Restore message list by moving from session to request scope.  Messages
    * are stored in the session to allow survival, through a redirect, for
    * display purposes.
    *
    * @param request The HTTP Request we are processing.
    */
   public static void restoreMessagesForDisplay(HttpServletRequest request) {
      HttpSession session = request.getSession();

      ArrayList messages = (ArrayList) session.getAttribute(Constants.MESSAGE_LIST_KEY);


      if(messages != null) {
         if(messages.size() > 0) {
            request.setAttribute(Constants.MESSAGE_LIST_KEY, messages);
         }


         session.removeAttribute(Constants.MESSAGE_LIST_KEY);
      }
   }

   /**
    * Determine if there are messages for display, stored in the session.  This
    * refers to messages managed using getMessagesForDisplay() and
    * restoreMessagesForDisplay().
    *
    * @param request The HTTP Request we are processing.
    *
    * @return true if there are messages for display; false otherwise
    */
   public static boolean existMessagesForDisplay(HttpServletRequest request) {
      HttpSession session = request.getSession();

      ArrayList messages = (ArrayList) session.getAttribute(Constants.MESSAGE_LIST_KEY);


      return ((messages != null) && (messages.size() > 0));
   }

   /**
    * Invalidate the session and redirect to Launch URL, supplied as a URL
    * parameter upon initial entry to the site, and stored in the session with
    * the LoginForm.  If there is no LoginForm or no launch URL in it, then we
    * throw an exception and the user is redirected to the App Unavailable
    * page.
    *
    * @param request The HTTP Request we are processing.
    * @param response The HTTP Response we are processing.
    *
    * @throws IOException in case of an IO exception
    * @throws RuntimeException in case the URL to redirect to is unavailable
    */
   public static void redirectToLaunchURL(HttpServletRequest request,
      HttpServletResponse response) throws IOException, RuntimeException {
      LoginForm loginForm = null;

      HttpSession session = request.getSession();


      // get loginForm from the session
      if(session != null) {
         loginForm = (LoginForm) session.getAttribute(Constants.LOGIN_KEY);
      }


      // invalidate the session
      if(session != null) {
         // session.invalidate() seems to not work in this
         // case.  Maybe it does not work after a redirect.
         CommonAction.invalidateSession(session);
      }


      // compute launch URL      
      String launchURL = (loginForm != null) ? loginForm.getLaunchURL() : null;


      // throw exception if launch URL not defined
      if(launchURL == null) {
         throw new RuntimeException("launchURL not defined in session!");
      }


      // redirect to launch URL
      response.sendRedirect(launchURL);
   }

   /**
    * Invalidate the session and redirect to Auth Entry URL, supplied as a URL
    * parameter upon initial entry to the site, and stored in a cookie sent
    * back to the client.  If the cookie was not sent with the request by the
    * client, then we throw an exception and the user is redirected to the App
    * Unavailable page.
    *
    * @param request The HTTP Request we are processing.
    * @param response The HTTP Response we are processing.
    *
    * @throws IOException in case of an IO exception
    * @throws RuntimeException in case the URL to redirect to is unavailable
    */
   public static void redirectToAuthEntryURL(HttpServletRequest request,
      HttpServletResponse response) throws IOException, RuntimeException {
      // define authEntryURL and look for value in cookie      
      String authEntryURL = Util.getCookieValue(request,
            Constants.AUTH_ENTRY_URL_COOKIE_NAME);


      // invalidate the session
      if(request.getSession() != null) {
         // session.invalidate() seems to not work in this
         // case.  Maybe it does not work after a redirect.
         CommonAction.invalidateSession(request.getSession());
      }


      // throw exception if authEntryURL not defined
      if(authEntryURL == null) {
         throw new RuntimeException(Constants.AUTH_ENTRY_URL_COOKIE_NAME
            + " cookie not supplied by client with request!");
      }


      // redirect to auth entry URL
      response.sendRedirect(authEntryURL);
   }
   /**
    * Load phone contacts for the current account from the business layer and
    * store in Session context.  If there is no current contact set in the
    * session, select the first active contact and place in the session as
    * current; if there are no active contacts, select the first inactive
    * contact and place in the session as current.  If there is already a
    * current contact in the session, try to match it with one in the list. If
    * there is a match (by ID or phone number), update the current contact
    * stored in the session to point to the one in the list which is loaded
    * from the business layer.  If no match can be made, remove the current
    * contact from the session.  If there is not already a sort order defined
    * in the session for active and inactive lists, create default sort orders
    * and store them in the session.
    *
    * @param session The session used to identify the account and to store the
    *        loaded contact info into
    * @param bl the implementation of business layer interface to use
    * @param accountSummary the account summary object containing the account
    *        number to get the list of contacts for, or null to have this
    *        function grab the account summary from the session
    *
    * @return true on success, false on error
    */
   public static boolean loadContactList(HttpSession session,
		      BusinessLayerInterface bl, AccountSummary accountSummary) {
	   return loadContactList(session, bl, accountSummary, null);
   }


   /**  
    * For TT item BUG00505 loadContactList is over loaded 
    * 
    * @param session The session used to identify the account and to store the
    *        loaded contact info into
    * @param bl the implementation of business layer interface to use
    * @param accountSummary the account summary object containing the account
    *        number to get the list of contacts for, or null to have this
    *        function grab the account summary from the session
    * @param outputDDO The GetPhoneContactsOutputDDO object         
    *
    * @return true on success, false on error
    */
   public static boolean loadContactList(HttpSession session,
      BusinessLayerInterface bl, AccountSummary accountSummary,GetPhoneContactsOutputDDO outputDDO) {
      boolean success = true;


      // get account summary from session if not supplied
      if(accountSummary == null) {
         accountSummary = (AccountSummary) session.getAttribute(Constants.ACCOUNT_SUMMARY_KEY);
      }


      // get phone contact list from business layer and store in session
      PhoneContactList phoneContactList = bl.getPhoneContactList(accountSummary, outputDDO, (FDRAgent) session.getAttribute(Constants.FDR_AGENT_KEY));

      session.setAttribute(Constants.CONTACT_LIST_KEY, phoneContactList);

      if(phoneContactList == null) {
         session.removeAttribute(Constants.CURRENT_CONTACT_KEY);

         session.removeAttribute(Constants.CONTACT_LIST_KEY);

         session.removeAttribute(Constants.ACTIVE_SORT_INFO_KEY);

         session.removeAttribute(Constants.INACTIVE_SORT_INFO_KEY);

         success = false;
      } else {
         // get sort infos from session; dangerously assume they are present
         SortInfoForm activeSortInfo = (SortInfoForm) session.getAttribute(Constants.ACTIVE_SORT_INFO_KEY);

         SortInfoForm inactiveSortInfo = (SortInfoForm) session.getAttribute(Constants.INACTIVE_SORT_INFO_KEY);


         // store sort infos in phone contact list
         phoneContactList.setActiveSortInfoForm(activeSortInfo);

         phoneContactList.setInactiveSortInfoForm(inactiveSortInfo);


         // get current contact from session    
         PhoneContactForm currentContact = (PhoneContactForm) session
            .getAttribute(Constants.CURRENT_CONTACT_KEY);

         PhoneContactForm matchingContact = null;


         // if there is no current contact, set to default, which is first
         // active, if there is one, or first inactive, or none
         if(currentContact == null) {
            session.removeAttribute(Constants.CURRENT_CONTACT_KEY);


            java.util.ArrayList list = phoneContactList.getActiveList();


            if(list.size() > 0) {
               session.setAttribute(Constants.CURRENT_CONTACT_KEY, list.get(0));
            } else {
               list = phoneContactList.getInactiveList();


               if(list.size() > 0) {
                  session.setAttribute(Constants.CURRENT_CONTACT_KEY,
                     list.get(0));
               }
            }

            // otherwise, ensure current contact in session matches one in list 
         } else {
            matchingContact = phoneContactList.findMatch(currentContact);


            // if matching contact is found, ensure the one from the list is
            // referenced from the session as the current contact
            if(matchingContact != null) {
               // this seems silly if the matching contact is the same as the one
               // already stored in the session, but consider what happens when a
               // contact is added.  The one in the session is a different object
               // from the one in the list just retrieved from the business layer.
               // thus, we are replacing the object referenced by the session with
               // a different object, the one from the list.  When first added
               // to the session, the new contact does not have an ID, but the one
               // which replaces it, from the list, does.
               session.setAttribute(Constants.CURRENT_CONTACT_KEY,
                  matchingContact);
            } else {
               // if no match is found (which should rarely happen), then ensure
               // there is no current contact stored in the session
               session.removeAttribute(Constants.CURRENT_CONTACT_KEY);
            }
         }

         // reload the memo notes and acquistion information for current contact
         currentContact = (PhoneContactForm) session.getAttribute(Constants.CURRENT_CONTACT_KEY);
         if(currentContact != null){
        	 success = success && bl.reloadPhoneContact(currentContact, accountSummary);
         }
      }

      return success;
   }

   /**
    * Invalidate a session manually by removing all session attributes.
    *
    * @param session The session to invalidate
    */
   public static void invalidateSession(HttpSession session) {
      Enumeration attributes = session.getAttributeNames();


      while(attributes.hasMoreElements()) {
         String key = (String) attributes.nextElement();

         session.removeAttribute(key);
      }
   }

   /**
    * Initialize session.
    *
    * @param session The session to initialize
    */
   public static void initializeSession(HttpSession session) {
      session.setAttribute(Constants.CURRENT_STATE_KEY,
         Constants.ST_INIT);
   }

   /**
    * Get object implementing interface to business layer from application
    * scope. This may be the "real" implementation of BusinessLayerInterface,
    * or it may be a "for-testing-only" version.
    *
    * @param request The HTTP servlet request being processed.
    *
    * @return a reference to the object used to interface with the business
    *         layer; this may be an object interfacing to the real business
    *         layer, or an object interfacing to a fack business layer
    */
   public static BusinessLayerInterface getBusinessLayerInterface(
      HttpServletRequest request) {
      return (BusinessLayerInterface) request.getSession().getServletContext()
                                             .getAttribute(Constants.BUSINESS_LAYER_KEY);
   }

   /**
    * Clear inactivation-specific fields in case contact is active; otherwise,
    * do nothing.  For fields having a corresponding label field (eg,
    * inactivationReason and inactivationReasonLabel), the label field is not
    * cleared.  It is assumed it will be cleared by some other code which is
    * executed after this method is called.
    *
    * @param contact The contact to modify if status is active
    */
   public static void clearInactivationFieldsIfActive(PhoneContactForm contact) {
      if(contact.getStatus().equalsIgnoreCase(Constants.CONTACT_STATUS_ACTIVE)) {
         contact.setInactivationReason(Constants.UNKNOWN_INACTIVATION_REASON);
         
         contact.setInactivationRequestedBy(Constants.UNKNOWN_REQUESTED_BY);

         contact.setReactivationFlag(Constants.FALSE_STRING);

         contact.setRequestType(Constants.UNKNOWN_REQUEST_TYPE);

         contact.getInactivationDate().setValue(Constants.NULL_STR_IN_FORM);

         contact.getReactivationDate().setValue(Constants.NULL_STR_IN_FORM);
      }
   }

   /**
    * Perform field-level validation on Add/Edit form submittal.  Validation
    * errors are recorded by addition of ActionError objects to errors object
    * stored in session.  This method ties in with the mechanism for
    * simulating error conditions for testing and diagnostics.  Also, blank
    * out the reactivation date if reactivation flag is false.
    *
    * @param request the request being processed
    * @param contact the current contact, with user-supplied edits, which the
    *        user is trying to save if all edits pass this validation check
    * @param originalContact in case the user is trying to Edit a contact (as
    *        opposed to Adding a new contact), this is the original contact
    *        before user-supplied changes
    */
   public static void validateAddEditFields(HttpServletRequest request,
      PhoneContactForm contact, PhoneContactForm originalContact) {

      String errMsgKey = null;

      // used to short-circuit validation of phone number if not supplied
      boolean phoneNumberSupplied = !contact.getPhoneNumber()
                                            .isPhoneNumberBlank();


      // used to validate reactivation date, which is required if flag is set
      boolean reactFlag = contact.getReactivationFlag().equals(Constants.TRUE_STRING);


      if(!reactFlag) {
         contact.getReactivationDate().setValue(Constants.NULL_STR_IN_FORM);
      }


      // used to short-circuit validation of reactivation date if not supplied
      boolean reactDateSupplied = !contact.getReactivationDate().getIsBlank();


      // used to validate fields in case of an inactive contact
      boolean isInactive = contact.getStatus().equalsIgnoreCase(Constants.CONTACT_STATUS_INACTIVE);


      // used to validate type of request and inactivation requested by, which
      // are required for certain values of inactivation reason
      boolean doesReasonRequireMoreInfo = isInactive
         && Util.regex(contact.getInactivationReason(),
            Constants.INACTIVATION_REASONS_REQUIRING_MORE_INFO);


      //--------
      // get errors object from session, to which errors may be added
      ActionErrors errors = getErrorsForDisplay(request);

      /** front-end validation:
      * blank phone number, invalid phone number
      * For CCMS 2.40, these checks were removed from here because an API called
      * in CustomerTelephoneManager did the checks. However due to BUG00254, the 
      * checks have been added back in. A redesign should be done to move these checks
      * to the JSP level if possible.
      * 
     **/
      // validate phone number: it must be supplied
      errMsgKey = "error.phone_number.not_supplied";


      if(!phoneNumberSupplied) {
         errors.add("phoneNumber", new ActionError(errMsgKey));
      }


      // validate phone number: it must be valid if supplied
      errMsgKey = "error.phone_number.invalid";

      if(phoneNumberSupplied) {
         if(!contact.getPhoneNumber().isPhoneNumberValid()) {
            errors.add("phoneNumber", new ActionError(errMsgKey));
         }
      }

      // validate phone number extension: it must be valid if supplied
      errMsgKey = "error.phone_number_extension.invalid";


      if(!contact.getPhoneNumber().isExtensionValid()) {
         errors.add("phoneNumberExt", new ActionError(errMsgKey));
      }

      // validate owner: it must be supplied
      errMsgKey = "error.owner.not_supplied";


      if(contact.getOwner().equals(Constants.UNKNOWN_OWNER)) {
         errors.add("owner", new ActionError(errMsgKey));
      }


      // validate type: it must be supplied
      errMsgKey = "error.type.not_supplied";


      if(contact.getType().equals(Constants.UNKNOWN_PHONE_CONTACT_TYPE)) {
         errors.add("type", new ActionError(errMsgKey));
      }
      
      Integer consentStatusId = contact.getNewConsentStatusId();
      //First name and last name are required for type of cell when ASC = Yes
      if(consentStatusId != null && Integer.valueOf(consentStatusId) == 1
    		  && !isInactive){ //only for active status
    	// validate first name: it must be supplied
          errMsgKey = "error.first_name.not_supplied";

          if(FormatUtil.isNamesBlank(contact.getFirstName())) {
             errors.add("firstName", new ActionError(errMsgKey));
          }
          
          
          // validate last name: it must be supplied
          errMsgKey = "error.last_name.not_supplied";

          if(FormatUtil.isNamesBlank(contact.getLastName())) {
             errors.add("lastName", new ActionError(errMsgKey));
          }
      }
      
      // validate first name: it must be valid
      errMsgKey = "error.first_name.invalid";
      if(!FormatUtil.isNamesValid(contact.getFirstName())){
    	  errors.add("firstName", new ActionError(errMsgKey));
      }
      
      // validate last name: it must be valid
      errMsgKey = "error.last_name.invalid";
      if(!FormatUtil.isNamesValid(contact.getLastName())){
    	  errors.add("lastName", new ActionError(errMsgKey));
      }
      
      // validate restricted number does not have update FDR flag set to work or home 
      errMsgKey = "error.updateFDRFlag.not_allowed_restricted_contact";


      if(contact.getStatus().equalsIgnoreCase(Constants.CONTACT_STATUS_ACTIVE) 
        && (contact.getRestrictedUseFlag().equalsIgnoreCase(Constants.TRUE_STRING) 
        && (contact.getUpdateFDRFlag().equalsIgnoreCase("home") || contact.getUpdateFDRFlag().equalsIgnoreCase("work")))) {
         errors.add("updateFDRFlag", new ActionError(errMsgKey));
      }

      // validate inactivation reason: it must be supplied if status is inactive
      errMsgKey = "error.inact_reason.not_supplied";

      if(isInactive) {
         if(contact.getInactivationReason().equals(Constants.UNKNOWN_INACTIVATION_REASON)) {
            errors.add("inactivationReason", new ActionError(errMsgKey));
         }
      }

      //validate inactivate reason: the "Other" inactivate reason can not be selected.
      errMsgKey = "error.inact_reason.OTHER_not_allowed";
      
      if(isInactive) {
         if(contact.getInactivationReason().equals(Constants.OTHER_INACTIVATION_REASON)) {
            errors.add("inactivationReasonOther",new ActionError(errMsgKey));
         }
      }
      
      // validate inactivation requested by: it must be supplied for certain
      // values of inactivation reason
      errMsgKey = "error.inact_req.not_supplied";


      if(doesReasonRequireMoreInfo) {
         if(contact.getInactivationRequestedBy().equals(Constants.UNKNOWN_REQUESTED_BY)) {
            errors.add("inactivationRequestedBy", new ActionError(errMsgKey));
         }
      }


      // validate reactivation flag: it must be supplied if status is inactive
      errMsgKey = "error.react_flag.not_supplied";

      if(isInactive) {
         if((contact.getReactivationFlag().length() == 0)) {
            errors.add("reactivationFlag", new ActionError(errMsgKey));
         }
      }


      // validate reactivation date: it must be supplied if reactivation flag is set
      errMsgKey = "error.react_date.not_supplied";


      if(reactFlag && !reactDateSupplied) {
         errors.add("reactivationDate", new ActionError(errMsgKey));
      }


      // validate reactivation date: it must be valid if supplied
      errMsgKey = "error.react_date.invalid";

      if(reactDateSupplied) {
         boolean isValid = contact.getReactivationDate().getIsValid();

         TimeAndDate reactivationTimeAndDate = contact.getReactivationDate()
                                                      .getTimeAndDateValue();

         TimeAndDate nowTimeAndDate = new TimeAndDate();

         boolean isValidAndInTheFuture = isValid
            && (reactivationTimeAndDate.compareTo(nowTimeAndDate) > 0);


         if(!isValidAndInTheFuture) {
            errors.add("reactivationDate", new ActionError(errMsgKey));
         }
      }


      // validate type of request: it must be supplied for certain
      // values of inactivation reason
      errMsgKey = "error.request_type.not_supplied";

      if(doesReasonRequireMoreInfo) {
         if((contact.getRequestType().equals(Constants.UNKNOWN_REQUEST_TYPE))) {
            errors.add("requestType", new ActionError(errMsgKey));
         }
      }


      // validate memo: it must not contain markup tags if supplied
      errMsgKey = "error.memo.invalid";


      if(Util.regexMultiLine(contact.getMemo(), "^.*<[^>]*>.*$")) {
         errors.add("memo", new ActionError(errMsgKey));
      }


      // validate memo: it must fit into the database column
      errMsgKey = "error.memo.too_long";

      if(contact.getMemo().length() > Constants.MAX_MEMO_TEXT_LENGTH) {
         errors.add("memo",
            new ActionError(errMsgKey,
               new Integer(Constants.MAX_MEMO_TEXT_LENGTH)));
      }
      
      if(Constants.isPecFunctionEnabled()) {
          // valid PEC part, since the logic for this part is complex, separate it out for more readable concern
          validateAddEditFieldsForPEC(errors, contact, originalContact, isInactive);
      }

   }
   
   /**
    * Only validate PEC part for Add Edit fields.
    *
    * @param errors, contact, originalContact, isInactive
    * @return
    */
   private static void validateAddEditFieldsForPEC(ActionErrors errors, PhoneContactForm contact, PhoneContactForm originalContact, boolean isInactive) {
       
       String errMsgKey = null;
       
       // Only validate PEC when the phone type is Cell.
       if(Constants.PHONE_TYPE_CELL.equals(contact.getType())) {

           // validate phone number extension: May only be populated when the Type is NOT "Cell"
           errMsgKey = "error.phone_number_extension.not_allowed_on_cell";
           
           if(!"".equals(contact.getPhoneNumber().getExt().trim())) {
              errors.add("phoneNumberExtNotCell", new ActionError(errMsgKey));
           }
           
           //validate PEC: The phone number may not be edited when an Automated Systems Consent was previously specified on the number.
           errMsgKey = "error.updatePhoneNumber.not_allowed_pec_status";
               
           if(originalContact != null && !contact.getPhoneNumber().getValueWithoutExtension().
              equals(originalContact.getPhoneNumber().getValueWithoutExtension()) && !Constants.PEC_NS_DESC.equals(originalContact.getLastConsent())) {
              errors.add("PECPhoneNumberUpdate", new ActionError(errMsgKey));
           }
           
          //validate PEC: must indicate the Automated Systems Consent.
           errMsgKey = "error.pec.not_supplied";
           
           //Refer to CCMS3.10 document: "Display of PEC in CCMS.xls"
           // Send error when PEC is not entered while activating Phone with existing PEC  = NO or NSP
           if(Constants.UNKNOWN_PEC.equals(contact.getNewConsentStatusId()) && !isInactive && 
        		   (originalContact == null || Constants.PEC_NS_DESC.equals(originalContact.getLastConsent())
        				   ||(Constants.PEC_NO_DESC.equals(originalContact.getLastConsent()) && 
        						   Constants.CONTACT_STATUS_INACTIVE.equals(originalContact.getStatus()))) ) {
              errors.add("PECStatus", new ActionError(errMsgKey));
           }           
           //validate PEC: must indicate the method of the Automated Systems Consent 
           //when the method field is displayed.
           errMsgKey = "error.pec_method.not_supplied";
           
           if (Constants.UNKNOWN_PEC_METHOD.equals(contact.getNewPecMethodId())) {
               if (Constants.PEC_YES.equals(contact.getNewConsentStatusId()) || Constants.PEC_NO.equals(contact.getNewConsentStatusId())) {
                   errors.add("PECMethod", new ActionError(errMsgKey));
               } else if (Constants.PEC_NS.equals(contact.getNewConsentStatusId()) && (Constants.PEC_YES_DESC.equals(contact.getLastConsent()) || Constants.PEC_NO_DESC.equals(contact.getLastConsent()))) {
                   errors.add("PECMethod", new ActionError(errMsgKey));
               }
           }
           
            //For TT Item 486 changed the condition to Pec_No
           //validate PEC: No FDR update allowed on phone numbers when Automated Systems Consent set to "No".
           errMsgKey = "error.updateFDRFlag.not_allowed_pec_status";

           if((Constants.PEC_NO.equals(contact.getNewConsentStatusId()) 
                   || (Constants.PEC_NO_DESC.equals(contact.getLastConsent()) 
                      && Constants.UNKNOWN_PEC.equals(contact.getNewConsentStatusId())))  
                  && !Constants.FDRFLAG_DEFAULT.equals(contact.getUpdateFDRFlag())
                  && contact.getStatus().equalsIgnoreCase(Constants.CONTACT_STATUS_ACTIVE)) {
              errors.add("PECUpdateFDRFlag", new ActionError(errMsgKey));
           }
           //For TT item 482 cease communication name is changed to cease communication cm 
       }else if(isInactive && (contact.getInactivationReason().equals(Constants.INACTIVATION_REASON_DNC_ALL_NUMBERS)
              || contact.getInactivationReason().equals(Constants.INACTIVATION_REASON_CEASE_COMMUNICATION_CM)) &&
              Constants.PEC_NO.equals(contact.getNewConsentStatusId()) &&
              Constants.UNKNOWN_PEC_METHOD.equals(contact.getNewPecMethodId())) {
          errMsgKey = "error.pec_method.not_supplied";
          errors.add("PECMethod", new ActionError(errMsgKey));
       }
   }

   /**
    * Get DWBUIDs from AccountSummary.  This method is used to display error message for "remove.number.from.dialer".
    * @param accountSummary
    * @return
    */
   public static String getDWBUIDs(AccountSummary accountSummary){
       List<Long> dwbuids = accountSummary.getDataWarehouseIdsForCurrentParty();
       StringBuilder sb = new StringBuilder();
       
       Iterator<Long> iter = dwbuids.iterator();
       while (iter.hasNext()) {
           sb.append(iter.next()).append("<br />");
       }
       
	   return sb.length() != 0 ? sb.substring(0, sb.length()-6) : sb.toString();
   }
   
   /**
    * Util method to change session state when find forward
    * @param request
    * @param mapping
    * @param forwardName
    * @param nextState
    * @return ActionForward
    */
   public static ActionForward findForward(HttpServletRequest request, ActionMapping mapping, String forwardName, String nextState) {
       if (nextState != null) {
           request.getSession().setAttribute(Constants.CURRENT_STATE_KEY, nextState);
       }
       
       return mapping.findForward(forwardName);
   }
   
   /**
    * Util method to check whether has current contact in session
    * 
    * @param request
    * @return boolean
    */
   public static boolean hasCurrentContact(HttpServletRequest request) {
       return request.getSession().getAttribute(Constants.CURRENT_CONTACT_KEY) != null;
   }
   
}

// end file CommonAction.java
