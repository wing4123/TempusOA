/*
* form表单序列化
* */
(function($){
    $.fn.serializeJSON=function(){
        var serializeObj={};
        var array=this.serializeArray();
        var str=this.serialize();
        $.each(array,function(){
            if(serializeObj[this.name]!=undefined){
                if($.isArray(serializeObj[this.name])){
                    serializeObj[this.name].push(this.value);
                }else{
                    serializeObj[this.name]=[serializeObj[this.name],this.value];
                }
            }else{
                serializeObj[this.name]=this.value;
            }
        });
        return serializeObj;
    };
})(jQuery);
