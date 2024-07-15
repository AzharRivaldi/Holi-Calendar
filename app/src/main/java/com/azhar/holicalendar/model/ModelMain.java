package com.azhar.holicalendar.model;

import java.io.Serializable;

/*
 * Created by Azhar Rivaldi on 12-02-2024
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

public class ModelMain implements Serializable {

    String strTanggal;
    String strKeterangan;
    String strCuti;

    public String getStrTanggal() {
        return strTanggal;
    }

    public void setStrTanggal(String strTanggal) {
        this.strTanggal = strTanggal;
    }

    public String getStrKeterangan() {
        return strKeterangan;
    }

    public void setStrKeterangan(String strKeterangan) {
        this.strKeterangan = strKeterangan;
    }

    public String getStrCuti() {
        return strCuti;
    }

    public void setStrCuti(String strCuti) {
        this.strCuti = strCuti;
    }
}
