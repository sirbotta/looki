/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
  
 function getJsTimestamp( sqlDateStr ) {
            sqlDateStr=sqlDateStr.replace(" ","-");
        sqlDateStr=sqlDateStr.replace(":","-");
        sqlDateStr=sqlDateStr.replace(".","-");
        
        var YMDhms = sqlDateStr.split("-");
        var sqlDate = new Date();
        sqlDate.setFullYear(parseInt(YMDhms[0]), parseInt(YMDhms[1])-1,
                                                 parseInt(YMDhms[2]));
        sqlDate.setHours(parseInt(YMDhms[3]), parseInt(YMDhms[4]), 
                                              parseInt(YMDhms[5]), 0/*msValue*/);
            
            return sqlDate;
    
}   
    
    
  
 $(".timer").each(function(){
     var tmp = $(this).text();
     
    $(this).countdown({until: getJsTimestamp(tmp),compact: true,description: ''})  ;
}) 
  
  
 
});


