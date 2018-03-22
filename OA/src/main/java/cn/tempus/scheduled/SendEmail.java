//package cn.tempus.scheduled;
//
//import cn.tempus.commons.GlobalCls;
//import cn.tempus.dao.EasyDao;
//import cn.tempus.email.Email;
//
//import org.apache.commons.mail.HtmlEmail;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Iterator;
//
///*
//@Scheduled(fixedDelay = 5000)
//以上的方法将以一个固定延迟时间5秒钟调用一次执行，这个周期是以上一个调用任务的完成时间为基准，在上一个任务完成之后，5s后再次执行
//@Scheduled(fixedRate = 5000)
//以上方法将以一个固定速率5s来调用一次执行，这个周期是以上一个任务开始时间为基准，从上一任务开始执行后5s再次调用
//
//@Scheduled(cron = "0 0 2 * * ?")　　//每天凌晨两点执行
//一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。
//按顺序依次为
//秒（0~59）
//分钟（0~59）
//小时（0~23）
//天（月）（0~31，但是你需要考虑你月的天数）
//月（0~11）
//天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
//7.年份（1970－2099）
//
//其中每个元素可以是一个值(如6),一个连续区间(9-12),一个间隔时间(8-18/4)(/表示每隔4小时),一个列表(1,3,5),通配符。由于"月份中的日期"和"星期中的日期"这两个元素互斥的,必须要对其中一个设置?.
//0 0 10,14,16 * * ? 每天上午10点，下午2点，4点
//0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时
//0 0 12 ? * WED 表示每个星期三中午12点 
//"0 0 12 * * ?" 每天中午12点触发 
//"0 15 10 ? * *" 每天上午10:15触发 
//"0 15 10 * * ?" 每天上午10:15触发 
//"0 15 10 * * ? *" 每天上午10:15触发 
//"0 15 10 * * ? 2005" 2005年的每天上午10:15触发 
//"0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发 
//"0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发 
//"0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发 
//"0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发 
//"0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发 
//"0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发 
//"0 15 10 15 * ?" 每月15日上午10:15触发 
//"0 15 10 L * ?" 每月最后一日的上午10:15触发 
//"0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发 
//"0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发 
//"0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发 
//有些子表达式能包含一些范围或列表
//例如：子表达式（天（星期））可以为 “MON-FRI”，“MON，WED，FRI”，“MON-WED,SAT”
//“*”字符代表所有可能的值
//因此，“*”在子表达式（月）里表示每个月的含义，“*”在子表达式（天（星期））表示星期的每一天
// 
//“/”字符用来指定数值的增量
//例如：在子表达式（分钟）里的“0/15”表示从第0分钟开始，每15分钟
//         在子表达式（分钟）里的“3/20”表示从第3分钟开始，每20分钟（它和“3，23，43”）的含义一样
//
//“？”字符仅被用于天（月）和天（星期）两个子表达式，表示不指定值
//当2个子表达式其中之一被指定了值以后，为了避免冲突，需要将另一个子表达式的值设为“？”
// 
//“L” 字符仅被用于天（月）和天（星期）两个子表达式，它是单词“last”的缩写
//但是它在两个子表达式里的含义是不同的。
//在天（月）子表达式中，“L”表示一个月的最后一天
//在天（星期）自表达式中，“L”表示一个星期的最后一天，也就是SAT
//如果在“L”前有具体的内容，它就具有其他的含义了
//例如：“6L”表示这个月的倒数第６天，“ＦＲＩＬ”表示这个月的最一个星期五
//注意：在使用“L”参数时，不要指定列表或范围，因为这会导致问题
// 
//字段   允许值   允许的特殊字符
//秒	 	0-59	 	, - * /
//分	 	0-59	 	, - * /
//小时	 	0-23	 	, - * /
//日期	 	1-31	 	, - * ? / L W C
//月份	 	1-12 或者 JAN-DEC	 	, - * /
//星期	 	1-7 或者 SUN-SAT	 	, - * ? / L C #
//年（可选）	 	留空, 1970-2099	 	, - * / 
// */
//@Component
//public class SendEmail {
//	
//	protected static Logger logger = LoggerFactory.getLogger(SendEmail.class);
//
//    @Autowired
//    EasyDao basicService;
//
//    @Autowired
//    public Email email;
//
//    /**
//     * 每天00:00:00 (午夜12点)定时发送
//     */
//    @Scheduled(cron = "0 0 0 * * ?")
//    public void sendEmail(){
//        String sql = "select b.user_id fid,b.user_name fname,b.email_address femail,count(*) fcount from(select distinct * from (select a.id_ taskid,b.user_id_ userid from act_ru_task a inner join act_ru_identitylink b on b.task_id_=a.id_ union select id_ taskid,assignee_ userid from act_ru_task where assignee_ is not null)) a inner join tb_user b on b.user_id=a.userid group by b.user_id,b.user_name,b.email_address";
//        Iterator<HashMap<String, Object>> list = basicService.SelectListBySql(sql).iterator();
//
//        try {
//            while (list.hasNext()){
//                HashMap<String,Object> userinfo = list.next();
//                if (userinfo.get("FEMAIL") != null) {
//                	HtmlEmail htmlemail = email.getHtmlEmail();
//                    htmlemail.addTo(userinfo.get("FEMAIL").toString(),userinfo.get("FEMAIL").toString());
//                    htmlemail.setSubject("待办任务提醒 "+userinfo.get("FCOUNT")+"个");
//                    htmlemail.setHtmlMsg("<html><div>Dear "+userinfo.get("FNAME")+",</div><div style='text-indent:2em;'>腾邦控股业务申请与审批系统中有"+userinfo.get("FCOUNT")+"个申请正在等待您审批，为避免业务延误，请您及时登录系统进行审核。谢谢。</div><a href='https://"+ GlobalCls.GP.getProperty("server.ip")+":"+GlobalCls.GP.getProperty("server.port")+"/OA/MyWorkFlow/MyToBeDo'>办理任务</a></html>");
//                    htmlemail.send();
//                    logger.info("email address:"+htmlemail.getToAddresses());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
//}
