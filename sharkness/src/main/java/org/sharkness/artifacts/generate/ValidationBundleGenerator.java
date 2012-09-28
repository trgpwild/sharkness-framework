package org.sharkness.artifacts.generate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.sharkness.business.factory.PropertiesFactory;

public class ValidationBundleGenerator extends BundleGenerator {

	public static void createBundle(String filePath) throws Exception {
		
		ready = new ArrayList<String>();
		
		String[] arr = filePath.split("\\/");
		
		String strfolder = filePath.replaceAll(arr[arr.length-1], "");
		
		File folder = new File(
			new StringBuilder(PropertiesFactory.getApplicationDevResources())
				.append("/").append(strfolder).toString()
		);
		
		if (!folder.exists()) folder.mkdirs();

		File messages = new File(
			new StringBuilder(PropertiesFactory.getApplicationDevResources())
				.append("/").append(filePath).toString()
		);
		
		if (!messages.exists()) messages.createNewFile();
		
		Properties old = new Properties();
		old.load(new FileInputStream(messages));
		
		Properties update = new SortedProperties();

		update = updateProperty("javax.faces.component.UIInput.REQUIRED","{0} - Field required.", update, old);
		update = updateProperty("javax.faces.component.UIInput.CONVERSION","{0} - Conversion error occurred.", update, old);
		update = updateProperty("javax.faces.component.UIInput.REQUIRED","{0} - Validation Error - Value is required.", update, old);
		update = updateProperty("javax.faces.component.UIInput.UPDATE","{0} - An error occurred when processing your submitted information.", update, old);
		update = updateProperty("javax.faces.component.UISelectMany.INVALID","{0} - Validation Error - Value is not valid.", update, old);
		update = updateProperty("javax.faces.component.UISelectOne.INVALID","{0} - Validation Error - Value is not valid.", update, old);
		update = updateProperty("javax.faces.converter.BigDecimalConverter.DECIMAL","{2} - ''{0}'' must be a signed decimal number.", update, old);
		update = updateProperty("javax.faces.converter.BigDecimalConverter.DECIMAL_detail","{2} - ''{0}'' must be a signed decimal number consisting of zero or more digits, that may be followed by a decimal point and fraction. Example - {1}", update, old);
		update = updateProperty("javax.faces.converter.BigIntegerConverter.BIGINTEGER","{2} - ''{0}'' must be a number consisting of one or more digits.", update, old);
		update = updateProperty("javax.faces.converter.BigIntegerConverter.BIGINTEGER_detail","{2} - ''{0}'' must be a number consisting of one or more digits. Example - {1}", update, old);
		update = updateProperty("javax.faces.converter.BooleanConverter.BOOLEAN","{1} - ''{0}'' must be 'true' or 'false'.", update, old);
		update = updateProperty("javax.faces.converter.BooleanConverter.BOOLEAN_detail","{1} - ''{0}'' must be 'true' or 'false'. Any value other than 'true' will evaluate to 'false'.", update, old);
		update = updateProperty("javax.faces.converter.ByteConverter.BYTE","{2} - ''{0}'' must be a number between 0 and 255.", update, old);
		update = updateProperty("javax.faces.converter.ByteConverter.BYTE_detail","{2} - ''{0}'' must be a number between 0 and 255. Example - {1}", update, old);
		update = updateProperty("javax.faces.converter.CharacterConverter.CHARACTER","{1} - ''{0}'' must be a valid character.", update, old);
		update = updateProperty("javax.faces.converter.CharacterConverter.CHARACTER_detail","{1} - ''{0}'' must be a valid ASCII character.", update, old);
		update = updateProperty("javax.faces.converter.DateTimeConverter.DATE","{2} - ''{0}'' could not be understood as a date.", update, old);
		update = updateProperty("javax.faces.converter.DateTimeConverter.DATETIME","{2} - ''{0}'' could not be understood as a date and time.", update, old);
		update = updateProperty("javax.faces.converter.DateTimeConverter.DATETIME_detail","{2} - ''{0}'' could not be understood as a date and time. Example - {1}", update, old);
		update = updateProperty("javax.faces.converter.DateTimeConverter.DATE_detail","{2} - ''{0}'' could not be understood as a date. Example - {1}", update, old);
		update = updateProperty("javax.faces.converter.DateTimeConverter.PATTERN_TYPE","{1} - A 'pattern' or 'type' attribute must be specified to convert the value ''{0}''.", update, old);
		update = updateProperty("javax.faces.converter.DateTimeConverter.TIME","{2} - ''{0}'' could not be understood as a time.", update, old);
		update = updateProperty("javax.faces.converter.DateTimeConverter.TIME_detail","{2} - ''{0}'' could not be understood as a time. Example - {1}", update, old);
		update = updateProperty("javax.faces.converter.DoubleConverter.DOUBLE","{2} - ''{0}'' must be a number consisting of one or more digits.", update, old);
		update = updateProperty("javax.faces.converter.DoubleConverter.DOUBLE_detail","{2} - ''{0}'' must be a number between 4.9E -324 and 1.7976931348623157E308 Example - {1}", update, old);
		update = updateProperty("javax.faces.converter.EnumConverter.ENUM","{2} - ''{0}'' must be convertible to an enum.", update, old);
		update = updateProperty("javax.faces.converter.EnumConverter.ENUM_NO_CLASS","{1} - ''{0}'' must be convertible to an enum from the enum, but no enum class provided.", update, old);
		update = updateProperty("javax.faces.converter.EnumConverter.ENUM_NO_CLASS_detail","{1} - ''{0}'' must be convertible to an enum from the enum, but no enum class provided.", update, old);
		update = updateProperty("javax.faces.converter.EnumConverter.ENUM_detail","{2} - ''{0}'' must be convertible to an enum from the enum that contains the constant ''{1}''.", update, old);
		update = updateProperty("javax.faces.converter.FloatConverter.FLOAT","{2} - ''{0}'' must be a number consisting of one or more digits.", update, old);
		update = updateProperty("javax.faces.converter.FloatConverter.FLOAT_detail","{2} - ''{0}'' must be a number between 1.4E -45 and 3.4028235E38 Example - {1}", update, old);
		update = updateProperty("javax.faces.converter.IntegerConverter.INTEGER","{2} - ''{0}'' must be a number consisting of one or more digits.", update, old);
		update = updateProperty("javax.faces.converter.IntegerConverter.INTEGER_detail","{2} - ''{0}'' must be a number between -2147483648 and 2147483647 Example - {1}", update, old);
		update = updateProperty("javax.faces.converter.LongConverter.LONG","{2} - ''{0}'' must be a number consisting of one or more digits.", update, old);
		update = updateProperty("javax.faces.converter.LongConverter.LONG_detail","{2} - ''{0}'' must be a number between -9223372036854775808 to 9223372036854775807 Example - {1}", update, old);
		update = updateProperty("javax.faces.converter.NumberConverter.CURRENCY","{2} - ''{0}'' could not be understood as a currency value.", update, old);
		update = updateProperty("javax.faces.converter.NumberConverter.CURRENCY_detail","{2} - ''{0}'' could not be understood as a currency value. Example - {1}", update, old);
		update = updateProperty("javax.faces.converter.NumberConverter.NUMBER","{2} - ''{0}'' is not a number.", update, old);
		update = updateProperty("javax.faces.converter.NumberConverter.NUMBER_detail","{2} - ''{0}'' is not a number. Example - {1}", update, old);
		update = updateProperty("javax.faces.converter.NumberConverter.PATTERN","{2} - ''{0}'' is not a number pattern.", update, old);
		update = updateProperty("javax.faces.converter.NumberConverter.PATTERN_detail","{2} - ''{0}'' is not a number pattern. Example - {1}", update, old);
		update = updateProperty("javax.faces.converter.NumberConverter.PERCENT","{2} - ''{0}'' could not be understood as a percentage.", update, old);
		update = updateProperty("javax.faces.converter.NumberConverter.PERCENT_detail","{2} - ''{0}'' could not be understood as a percentage. Example - {1}", update, old);
		update = updateProperty("javax.faces.converter.STRING","{1} - Could not convert ''{0}'' to a string.", update, old);
		update = updateProperty("javax.faces.converter.ShortConverter.SHORT","{2} - ''{0}'' must be a number consisting of one or more digits.", update, old);
		update = updateProperty("javax.faces.converter.ShortConverter.SHORT_detail","{2} - ''{0}'' must be a number between -32768 and 32767 Example - {1}", update, old);
		update = updateProperty("javax.faces.validator.DoubleRangeValidator.MAXIMUM","{1} - Validation Error - Value is greater than allowable maximum of {0}", update, old);
		update = updateProperty("javax.faces.validator.DoubleRangeValidator.MINIMUM","{1} - Validation Error - Value is less than allowable minimum of ''{0}''", update, old);
		update = updateProperty("javax.faces.validator.DoubleRangeValidator.NOT_IN_RANGE","{2} - Validation Error - Specified attribute is not between the expected values of {0} and {1}.", update, old);
		update = updateProperty("javax.faces.validator.DoubleRangeValidator.TYPE","{0} - Validation Error - Value is not of the correct type", update, old);
		update = updateProperty("javax.faces.validator.LengthValidator.MAXIMUM","{1} - Validation Error - Value is greater than allowable maximum of ''{0}''", update, old);
		update = updateProperty("javax.faces.validator.LengthValidator.MINIMUM","{1} - Validation Error - Value is less than allowable minimum of ''{0}''", update, old);
		update = updateProperty("javax.faces.validator.LongRangeValidator.MAXIMUM","{1} - Validation Error - Value is greater than allowable maximum of ''{0}''", update, old);
		update = updateProperty("javax.faces.validator.LongRangeValidator.MINIMUM","{1} - Validation Error - Value is less than allowable minimum of ''{0}''", update, old);
		update = updateProperty("javax.faces.validator.LongRangeValidator.NOT_IN_RANGE","{2} - Validation Error - Specified attribute is not between the expected values of {0} and {1}.", update, old);
		update = updateProperty("javax.faces.validator.LongRangeValidator.TYPE","{0} - Validation Error - Value is not of the correct type.", update, old);
		update = updateProperty("javax.faces.validator.NOT_IN_RANGE","Validation Error - Specified attribute is not between the expected values of {0} and {1}.", update, old);
		update = updateProperty("sharkness.cnpj.validate.exception", "Invalid CNPJ", update, old);
		update = updateProperty("sharkness.cpf.validate.exception", "Invalid CPF", update, old);
		update = updateProperty("sharkness.email.validate.exception", "Invalid Email", update, old);
				
		for (String key : old.stringPropertyNames()) {
			if (!ready.contains(key)) update.put(key, old.getProperty(key));
		}
		
		update.store(new FileOutputStream(messages), "Internationalization by Sharkness Framework");

	}

}