log_tag="test2.lua";

function getPhoneNumber()
	return text..context:getTelephonyManager():getLine1Number();
end