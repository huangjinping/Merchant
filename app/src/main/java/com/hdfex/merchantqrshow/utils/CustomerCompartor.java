package com.hdfex.merchantqrshow.utils;

import com.hdfex.merchantqrshow.bean.salesman.customer.CustomerResult;
import java.util.Comparator;

/**
 * Created by harrishuang on 16/10/14.
 */

public class CustomerCompartor implements Comparator<CustomerResult>{

    @Override
    public int compare(CustomerResult lhs, CustomerResult rhs) {
        int i = lhs.getPhoneticName().compareTo(rhs.getPhoneticName());
        return i;
    }
}
