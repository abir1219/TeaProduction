package com.tea.teaproduction.ui.StockManagement.Model;

public class StockModel {
    int StockId;
    String ItemId,ItemCategoryId,CompanyId,ItemRate,SGST,CGST,IGST,ItemTotal,PurchaseDate,PurchaseRemark,StockIn,DispatchDate,
    DispatchRemark,StockOut,InvoiceNumber,InvoiceDate,UnitPrice,CustomPrice1,CustompriceValue1,CustomPrice2,CustompriceValue2,
            CustomPrice3,CustompriceValue3,Available;


    public StockModel(int stockId, String itemId, String itemCategoryId, String companyId, String itemRate, String SGST, String CGST, String IGST, String itemTotal, String purchaseDate, String purchaseRemark,
                      String stockIn, String dispatchDate, String dispatchRemark, String stockOut, String invoiceNumber, String invoiceDate, String unitPrice, String customPrice1, String custompriceValue1, String customPrice2, String custompriceValue2, String customPrice3, String custompriceValue3, String available) {
        StockId = stockId;
        ItemId = itemId;
        ItemCategoryId = itemCategoryId;
        CompanyId = companyId;
        ItemRate = itemRate;
        this.SGST = SGST;
        this.CGST = CGST;
        this.IGST = IGST;
        ItemTotal = itemTotal;
        PurchaseDate = purchaseDate;
        PurchaseRemark = purchaseRemark;
        StockIn = stockIn;
        DispatchDate = dispatchDate;
        DispatchRemark = dispatchRemark;
        StockOut = stockOut;
        InvoiceNumber = invoiceNumber;
        InvoiceDate = invoiceDate;
        UnitPrice = unitPrice;
        CustomPrice1 = customPrice1;
        CustompriceValue1 = custompriceValue1;
        CustomPrice2 = customPrice2;
        CustompriceValue2 = custompriceValue2;
        CustomPrice3 = customPrice3;
        CustompriceValue3 = custompriceValue3;
        Available = available;
    }

    public int getStockId() {
        return StockId;
    }

    public String getItemId() {
        return ItemId;
    }

    public String getItemCategoryId() {
        return ItemCategoryId;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public String getItemRate() {
        return ItemRate;
    }

    public String getSGST() {
        return SGST;
    }

    public String getCGST() {
        return CGST;
    }

    public String getIGST() {
        return IGST;
    }

    public String getItemTotal() {
        return ItemTotal;
    }

    public String getPurchaseDate() {
        return PurchaseDate;
    }

    public String getPurchaseRemark() {
        return PurchaseRemark;
    }

    public String getStockIn() {
        return StockIn;
    }

    public String getDispatchDate() {
        return DispatchDate;
    }

    public String getDispatchRemark() {
        return DispatchRemark;
    }

    public String getStockOut() {
        return StockOut;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public String getInvoiceDate() {
        return InvoiceDate;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public String getCustomPrice1() {
        return CustomPrice1;
    }

    public String getCustompriceValue1() {
        return CustompriceValue1;
    }

    public String getCustomPrice2() {
        return CustomPrice2;
    }

    public String getCustompriceValue2() {
        return CustompriceValue2;
    }

    public String getCustomPrice3() {
        return CustomPrice3;
    }

    public String getCustompriceValue3() {
        return CustompriceValue3;
    }

    public String getAvailable() {
        return Available;
    }
}
