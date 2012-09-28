package org.sharkness.helper;

public class BrasilValidation {

	public static Boolean isCnpj(String str_cnpj) {
		try {
			int soma = 0, dig;
			String cnpj_calc = str_cnpj.substring(0, 12);
			if (str_cnpj.length() != 14) return false;
			char[] chr_cnpj = str_cnpj.toCharArray();
			for (int i = 0; i < 4; i++)
				if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
					soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
			for (int i = 0; i < 8; i++)
				if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9)
					soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
			dig = 11 - (soma % 11);
			cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
			soma = 0;
			for (int i = 0; i < 5; i++)
				if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
					soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
			for (int i = 0; i < 8; i++)
				if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9)
					soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
			dig = 11 - (soma % 11);
			cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
			return str_cnpj.equals(cnpj_calc);
		} catch (Exception e) {
			return false;
		}
	}

    public static Boolean isCpf(String cpf) {
    	try {
            if (cpf.length() != 11) return false;  
            String numDig = cpf.substring(0, 9);  
            Integer primDig, segDig;  
            int soma = 0, peso = 10;  
            for (int i = 0; i < numDig.length(); i++)  
            	soma += Integer.parseInt(numDig.substring(i, i + 1)) * peso--;  
            if (soma % 11 == 0 | soma % 11 == 1)  
                primDig = new Integer(0);  
            else  
                primDig = new Integer(11 - (soma % 11));  
            soma = 0;  
            peso = 11;  
            for (int i = 0; i < numDig.length(); i++)  
            	soma += Integer.parseInt(numDig.substring(i, i + 1)) * peso--;  
            soma += primDig.intValue() * 2;  
            if (soma % 11 == 0 | soma % 11 == 1) segDig = new Integer(0);  
            else segDig = new Integer(11 - (soma % 11));  
            return (primDig.toString() + segDig.toString()).equals(cpf.substring(9, 11));  
		} catch (Exception e) {
			return false;
		}
    }
    
    public static String toCpfCnpj(String cpfCnpj) {
    	int qtd = cpfCnpj.length();
    	int dif = 14 - qtd;
    	StringBuilder prefix = new StringBuilder();
    	for (int i = 0; i < dif; i++) {
			prefix.append("0");
		}
    	StringBuilder complete = new StringBuilder();
    	return complete.append(prefix.toString()).append(cpfCnpj).toString();
    }
    
}