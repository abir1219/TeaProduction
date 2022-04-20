package com.tea.teaproduction.utils;

public class Urls {
    public static String BASE_URL = "http://sketchmeglobal.com/tea/user/";


    /*
    @Function : consignment_add
    @Method : POST
    @Parameter : emp_id,emp_code,category,shift,sector,weight,picture,date
     */
    public static String ADD_CONSIGNMENT = BASE_URL + "consignment_add";

    /*
    @Function : category
    @Method : POST
     */
    public static final String CATEGORY = BASE_URL + "category";

    /*
    @Function : shift
    @Method : POST
     */
    public static final String SHIFT = BASE_URL + "shift";

    /*
    @Function : sector
    @Method : POST
     */
    public static final String SECTOR = BASE_URL + "sector";

    /*
    @Function : get_all_employee
    @Method : POST
     */
    public static final String GET_ALL_EMPLOYEE = BASE_URL + "get_all_employee";

    /*
    @Function : get_employee
    @Method : POST
    @ Parameter : emp_code
     */
    public static final String GET_EMPLOYEE = BASE_URL + "get_employee";

    /*
        @Function : consignment_add
        @Method : POST
        @ Parameter :
         */
    public static final String CONSIGNMENT_ADD = BASE_URL + "consignment_add";


    /*
    @Function : consignment_list
    @Method : POST
     */
    public static final String CONSIGNMENT_LIST = BASE_URL + "consignment_list";
}
