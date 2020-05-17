package com.ruoyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author luckymall
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RuoYiApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  LuckyMall启动成功   ლ(´ڡ`ლ)ﾞ \n " +
                ";##W#############WWWWWWKGEKWWKWKWWWWWWWWWWKKKWKWj,\n" +
                ";#################WW#WDjtttjDKWWKWWWWWKWWKWKKKKWj,\n" +
                ";##########W##WWWWWW#Gjtii;;ijK#KKWWWWWWKKKWWWWKj,\n" +
                ",############W##WWW#Wfjii;;;;itLWWWWWWWWWKKKKKWWj,\n" +
                ",i####WWW#####WWWW##Dfji;;;fGf;GWKWWWWWWWKKKWWWWj,\n" +
                "::##W#W####WWWWWWW##Lfffi;ijDj;tWKWWWWWKWWWWWKWWj,\n" +
                ",,###########WWWWW#KLfLLtiitji,iWEWWWWKWKKKKKKKWj,\n" +
                ":,W#WW####WWWWWWWWWGGfGLtj;;,,,;WWKWWWWKWWWWWWWWj,\n" +
                ".;G#########WWWWWKWWGjti;j;,,,,KWWKWKKKWWWWWWKWWf,\n" +
                ":;i##W#WWWWWWWWWWKKWjft;;j;;,,,KWWEKKWWWWWWWKWWWj,\n" +
                "jjj##W##W#WWWWWWWKWWWWt;,ti;,,,WWKKKWWWWWWKWWKWKj,\n" +
                "GGD####WWWWWWWWWKKKWWWji;i;;;,,WKKKKKWKWWWWWWWWWf,\n" +
                "ij####W#WWWWWWWWWKWWWWWi;tft,,;#WWKKKWWKKWWWWKWWj,\n" +
                "#########WWWWWWWWWWWWWW#ii;,,,,tEWWKKKEEKKKWWWWKj,\n" +
                "######WWWWWWWWWWWWWWW##Kjti,,,,,,,,..WWWWWKWWWWWj,\n" +
                "W########WWWWWWWWWWWW#Dfttt;,,,::::.:,,,WWWWKKKKj,\n" +
                "W########WWWWWWWWW###Gjti;;,:,::::,.::,,,WKKWKKWf;\n" +
                "##########WWWW###W#iiti;;;;,:::,,::,.::::,WKWWWWf;\n" +
                "##########WWWWWKKW#,iii;;,;::,,,::::..::::WKKKKWf;\n" +
                "#########WKKKKKKKLi.;ii;;;,,,:::::::..::::jWWKKKf;\n" +
                "######KKKKKKKKKKf;;.;;;,,::::::::::.  ::::,KKKWWf;\n" +
                "###EEEKKKKKKKKEEi,,.,;,,,,,,:::::::. .::::,KWWWKf;\n" +
                "KKKKKEKKKKKKEEKE;,:..;,,,,:::::::::. ::::::KWKWWf;\n" +
                "KKKKKKKKKEEEEEEE;,,::;,,,,:::::,::.. ::::::LfWKKf;\n" +
                "KKKEEKKEEEEEEEEDi,,.:;,,:,:::,,,::...:,::::LLLWWf;\n" +
                "KEEEKKKEEEEEDDEDi,,,:,,::::::;,,:...  ;::::LLLfWj;\n" +
                "KEEEEKEEEEEEDEEEj;,,:;,,,::::;,::..   .,:::;LLLfj;\n" +
                "EKEKEEEEEEEEEEEKK;,:,i,,,::::;,:..     ,:::,LLGLfi\n" +
                "EEEEEEEEEEEEKKKWWt,:;,,,:::.:,:..    ..,:::,fLGLLf\n" +
                "EEEEEEEEEEEKWWWWWf,:,,,:;....;::     ..;:::,LjLLLf\n" +
                "EEEEKEKKKWWWWWWWWW;:,;:.. ...,,..    .Gj,:::tLLLLL\n" +
                "DEEEEEKWWWWWWWWWKKi::j,:.. ..;..   ...KE,:::ijfLLL\n" +
                "EEEEEKWWWWWWWWKKKKi,:;,:...  .:.......KK;,::,jfLLL\n" +
                "EEEKKWWWWWWWWKKKKKt,:,j,:::.   ..:.. ;KKt;,:,fffLL\n" +
                "EKWWWWWWWWKKKEEEKKf,,,;E,,:     ::.:,jWKKi,,,jfffL\n" +
                "KKKDWWWWWKEEEEEEEKD,::,E;::: ::,::::;jWKK;,,,ffLff\n" +
                "GGDDWWWKEEDDDDEEEED;::,Gfi;;:::::::,;tWWj;,:,jLfLL\n" +
                "LDEWWWKKEDDDDDDDDDD,,::Lft;;,,:::,,;;tWWt,::,ttfLL\n" +
                "DEEWWKEEEDDDDDDEEEG,,::fjti;;,,,,,,,;jWWt,,,;titfL\n" +
                "DEWWKEDDDDDDDDDEKKj,,::jtti;;,,,,,,;ifDWi,:,fttijL\n" +
                "DEWEDDGGDDDDDDEEKKt,::::tii;;;;;,,,;tGDW;,,,jtiitf\n" +
                "EWEDDDDDEKKKWKWWWGt,::::.ii;;;;;,,;ifEEj;:,tttijtf\n" +
                "ttjGWW##########GLt,::,...,i;;;,,;ijGEEi,,;ftijtjf\n" +
                "ttLjtDEDGGGDDEKDjft,::,;....,ii;;itLEEW;,,Djtjjjff\n" +
                "tffLjDEGGGDEEEDftjt,::,;.......;itjGGGt,:,DLjjjjfj\n" +
                "GGLGDDGfLLLGDDjtijt,,,,;::........,tLGi,:;Gjitjjjf\n" +
                "EGLLGLGLGGGDEjti;jt,,,,;t:;iiiiiitjLGG;,,GLtittffj\n" +
                "DGLLLGGGGGDEfti;;ji,,;;iKftiiiiiitfGDj;,,Efti;tjjf\n" +
                "DGGLLLLDGDEfti;;,fi,,iif#jti;iiiitLDEjititjtijtjfL\n" +
                "DGGLfffLDEftti;,,Li,;fjjEti;;;;;itGEKjttiit;tfjffL\n" +
                "DGLLfffLDftt;;,,,L;,ijtjti;;;;;;ijDKLjtji;itjjfLff\n" +
                "DGLfffLGftii;;,,;f;;fjjj;;;;;;;;ifEWGtjf;E;jjffLLf\n" +
                "EDGfjfffjii;;;;;ifiifjLi;;;;,,;;tGW#DjjW,LtjjLLfLf\n" +
                "KEGfjjjttii;;;;ifjiffjji;;,,,;;ijEWKEtiD;fffLGLGLL\n" +
                "WWGfjjtii;;;;;ij#tiLffjt;,,,,;;tLKWKLitijiLffLLLff\n" +
                "#WGjjtti;;;;;ifKjiiDfjft;,,,;;ijE#WKDiL;EjfjLGLLjf\n" +
                "##Gfjtii;ii;ijLtiiiWLLji;,,,;ijGWWWWWiDLLLffLLLfti\n" +
                "#WELjiiiiiiijLtiiijKWft;,,,,;tGWWKWWLLGGGLLLLGfft;\n" +
                "##WKLtttttjDji;DjiGEKfi;,,,;iLKKKKWGLLffLfLGLLft,,\n" +
                "W###WGfffLfDtftEiDfDEfti;;;tLKKKKWGffjjGGLLLLftf,,\n" +
                "WWE##WLLLfGjiifLDfLLDDftitjDKKKKKEfjfLfLLLGLfjt;,,\n" +
                "WWWK#WWLGGLLjtfGfffLGGDDLLEKKKKKWLLGLffLGGLLfti;,,\n" +
                "WWWW##WKGfLEGjfLfLLLGGGGDDDEEKKKLjfGLLLGGGLftji,,,\n" +
                "WWWWW##WELLDLLfffLLLLLGGGDDEKKKLjjLLLGLLGGffti;,,;");
    }
}