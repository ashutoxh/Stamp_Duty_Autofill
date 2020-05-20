 'use strict';

    function injectScript() {
       document.getElementById("Dept_Code_Tax_id").value = "IGR|N|C";
       onChangeDepartment();
       document.getElementById("cmbRec_Type_list").value = "12|||N";
       onChangePaymentType();
       document.getElementById("cmbtrea_code").value = "1301";
       onDistrictChange();
       document.getElementById("office_code_list").value = "IGR110~IGR03";
       onChangeOffice();
       document.getElementById("cmbScheme_code").value = "0030046401";
       onChangeScheme("0030046401");
       document.getElementById("rperiod").value = "O";
       document.getElementById("cmbFormID").value = "29";
       document.getElementById("amount1").value = "10";
       document.getElementById("Gross_Tot").value = "10.00";
       document.getElementById("txtPANNo").value = "AAPFK7876D";
       document.getElementById("txtpartyname").value = "K B Impex";
       document.getElementById("txtprimise").value = "77 RJIP";
       document.getElementById("txtroad").value = "IGRL";
       document.getElementById("txtdist").value = "VILE PARLE";
       document.getElementById("txtPIN").value = "400056";
       document.getElementById("txtMobileNo").value = "9999999999";
       document.getElementById("remarks").value = "imported vide BOE No 7277312 Dtd 17.03.2020  IGM  2249542 ITEM 109";
    }