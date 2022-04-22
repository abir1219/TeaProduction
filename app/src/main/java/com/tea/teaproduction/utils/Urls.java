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

    /*
    @Function : company_list
    @Method : POST
     */
    public static final String COMPANY_LIST = BASE_URL + "company_list";

    /*
    @Function : item_category_list
    @Method : POST
     */
    public static final String ITEM_CATEGORY_LIST = BASE_URL + "item_category_list";

    /*
    @Function : item_list
    @Method : POST
     */
    public static final String ITEM_LIST = BASE_URL + "item_list";


    /*
    @Function : purchase_stock
    @Method : POST
    @Parameter : Item_id ,Item_category_id ,Company_id,sgst,igst,cgst,Puchase_date (required),puchase_remark,stock_in (required),nvoice_number (required),Invoice_date (required),Unit_price (required),custom_price1,custom_value1,custom_price2,custom_value2,custom_price3,custom_value3
     */
    public static final String PURCHASE_STOCK = BASE_URL + "purchase_stock";
}
