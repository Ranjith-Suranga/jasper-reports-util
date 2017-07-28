/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.jasper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author ranjith-suranga
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Report {
    public String value();
}
