package com.fjsdfx.yyd.everyday.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2017/1/19.
 */

public class AnedoteDate {

    /**
     * showapi_res_code : 0
     * showapi_res_error :
     * showapi_res_body : {"newslist":[{"title":"15张吓掉半条命的照片!看完不敢关灯","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132559.jpg","description":"猎奇奇闻","ctime":"2016-09-07 00:00","url":"http://www.lieqi.com/read/45/132559/"},{"title":"恐怖！游客住泰国酒店一晚后发现床垫下藏尸","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132320.jpg","description":"猎奇奇闻","ctime":"2016-08-23 00:00","url":"http://www.lieqi.com/read/45/132320/"},{"title":"广西桂平出现蛇精，回放：还有一只已经逃走了","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132345.jpg","description":"猎奇奇闻","ctime":"2016-08-24 00:00","url":"http://www.lieqi.com/read/45/132345/"},{"title":"太惊悚了！韩国女星鼻子一捏就坍塌了","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132378.jpg","description":"猎奇奇闻","ctime":"2016-08-25 00:00","url":"http://www.lieqi.com/read/45/132378/"},{"title":"女子废弃房屋前拍下照片，妈妈看了脸色大变！","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132436.jpg","description":"猎奇奇闻","ctime":"2016-08-30 00:00","url":"http://www.lieqi.com/read/45/132436/"},{"title":"古巴男子将制作超8180米长雪茄 挑战世界纪录","picUrl":"http://img9.lieqi.com/upload1/thumb/31/132166.jpg","description":"猎奇奇闻","ctime":"2016-08-11 00:00","url":"http://www.lieqi.com/read/45/132166/"},{"title":"中国18个最邪门的地方 你敢去看看吗?","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132170.jpg","description":"猎奇奇闻","ctime":"2016-08-12 00:00","url":"http://www.lieqi.com/read/45/132170/"},{"title":"老家怪事：胡同口的歪脖柳树克死每家的男人","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132186.jpg","description":"猎奇奇闻","ctime":"2016-08-12 00:00","url":"http://www.lieqi.com/read/45/132186/"},{"title":"震惊！人死后身体竟还会做这10件事","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132240.jpg","description":"猎奇奇闻","ctime":"2016-08-16 00:00","url":"http://www.lieqi.com/read/45/132240/"},{"title":"探秘阴森恐怖的墨西哥鬼娃岛 起因竟是鬼故事","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132252.jpg","description":"猎奇奇闻","ctime":"2016-08-17 00:00","url":"http://www.lieqi.com/read/45/132252/"},{"title":"让人头皮发麻的25张诡异照片","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132068.jpg","description":"猎奇奇闻","ctime":"2016-08-08 00:00","url":"http://www.lieqi.com/read/45/132068/"},{"title":"令人毛骨悚然的十大邪恶女鬼","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132069.jpg","description":"猎奇奇闻","ctime":"2016-08-05 00:00","url":"http://www.lieqi.com/read/45/132069/"},{"title":"北京的灵异地点及其传说","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132012.jpg","description":"猎奇奇闻","ctime":"2016-08-04 00:00","url":"http://www.lieqi.com/read/45/132012/"},{"title":"无解悬案：北极村庄1200多人集体消失","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132032.jpg","description":"猎奇奇闻","ctime":"2016-08-04 00:00","url":"http://www.lieqi.com/read/45/132032/"},{"title":"世界上最惊人诡异的20个巧合 细思极恐","picUrl":"http://img9.lieqi.com/upload1/thumb/28/131930.jpg","description":"猎奇奇闻","ctime":"2016-08-01 00:00","url":"http://www.lieqi.com/read/44/131930/"},{"title":"揭秘：人临死前将会有的十三种神秘感受","picUrl":"http://img9.lieqi.com/upload1/thumb/28/131846.jpg","description":"猎奇奇闻","ctime":"2016-07-28 00:00","url":"http://www.lieqi.com/read/44/131846/"},{"title":"真假？据说这些地方就是通往冥界的入口","picUrl":"http://img9.lieqi.com/upload1/thumb/28/131848.jpg","description":"猎奇奇闻","ctime":"2016-07-28 00:00","url":"http://www.lieqi.com/read/44/131848/"},{"title":"轰动全国的北京地铁真实灵异怪事：胆小勿进","picUrl":"http://img9.lieqi.com/upload1/thumb/28/131855.jpg","description":"猎奇奇闻","ctime":"2016-07-28 00:00","url":"http://www.lieqi.com/read/44/131855/"},{"title":"1987陕西夜狸猫事件 村庄里人全消失","picUrl":"http://img9.lieqi.com/upload1/thumb/28/131800.jpg","description":"猎奇奇闻","ctime":"2016-07-27 00:00","url":"http://www.lieqi.com/read/44/131800/"},{"title":"不可思议：被斩掉头的无头公鸡竟活18个月","picUrl":"http://img9.lieqi.com/upload1/thumb/28/131735.jpg","description":"猎奇奇闻","ctime":"2016-07-26 00:00","url":"http://www.lieqi.com/read/44/131735/"}],"code":200,"msg":"success"}
     */

    private int showapi_res_code;
    private String showapi_res_error;
    private ShowapiResBodyBean showapi_res_body;

    public static AnedoteDate objectFromData(String str) {

        return new Gson().fromJson(str, AnedoteDate.class);
    }

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {
        /**
         * newslist : [{"title":"15张吓掉半条命的照片!看完不敢关灯","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132559.jpg","description":"猎奇奇闻","ctime":"2016-09-07 00:00","url":"http://www.lieqi.com/read/45/132559/"},{"title":"恐怖！游客住泰国酒店一晚后发现床垫下藏尸","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132320.jpg","description":"猎奇奇闻","ctime":"2016-08-23 00:00","url":"http://www.lieqi.com/read/45/132320/"},{"title":"广西桂平出现蛇精，回放：还有一只已经逃走了","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132345.jpg","description":"猎奇奇闻","ctime":"2016-08-24 00:00","url":"http://www.lieqi.com/read/45/132345/"},{"title":"太惊悚了！韩国女星鼻子一捏就坍塌了","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132378.jpg","description":"猎奇奇闻","ctime":"2016-08-25 00:00","url":"http://www.lieqi.com/read/45/132378/"},{"title":"女子废弃房屋前拍下照片，妈妈看了脸色大变！","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132436.jpg","description":"猎奇奇闻","ctime":"2016-08-30 00:00","url":"http://www.lieqi.com/read/45/132436/"},{"title":"古巴男子将制作超8180米长雪茄 挑战世界纪录","picUrl":"http://img9.lieqi.com/upload1/thumb/31/132166.jpg","description":"猎奇奇闻","ctime":"2016-08-11 00:00","url":"http://www.lieqi.com/read/45/132166/"},{"title":"中国18个最邪门的地方 你敢去看看吗?","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132170.jpg","description":"猎奇奇闻","ctime":"2016-08-12 00:00","url":"http://www.lieqi.com/read/45/132170/"},{"title":"老家怪事：胡同口的歪脖柳树克死每家的男人","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132186.jpg","description":"猎奇奇闻","ctime":"2016-08-12 00:00","url":"http://www.lieqi.com/read/45/132186/"},{"title":"震惊！人死后身体竟还会做这10件事","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132240.jpg","description":"猎奇奇闻","ctime":"2016-08-16 00:00","url":"http://www.lieqi.com/read/45/132240/"},{"title":"探秘阴森恐怖的墨西哥鬼娃岛 起因竟是鬼故事","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132252.jpg","description":"猎奇奇闻","ctime":"2016-08-17 00:00","url":"http://www.lieqi.com/read/45/132252/"},{"title":"让人头皮发麻的25张诡异照片","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132068.jpg","description":"猎奇奇闻","ctime":"2016-08-08 00:00","url":"http://www.lieqi.com/read/45/132068/"},{"title":"令人毛骨悚然的十大邪恶女鬼","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132069.jpg","description":"猎奇奇闻","ctime":"2016-08-05 00:00","url":"http://www.lieqi.com/read/45/132069/"},{"title":"北京的灵异地点及其传说","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132012.jpg","description":"猎奇奇闻","ctime":"2016-08-04 00:00","url":"http://www.lieqi.com/read/45/132012/"},{"title":"无解悬案：北极村庄1200多人集体消失","picUrl":"http://img9.lieqi.com/upload1/thumb/28/132032.jpg","description":"猎奇奇闻","ctime":"2016-08-04 00:00","url":"http://www.lieqi.com/read/45/132032/"},{"title":"世界上最惊人诡异的20个巧合 细思极恐","picUrl":"http://img9.lieqi.com/upload1/thumb/28/131930.jpg","description":"猎奇奇闻","ctime":"2016-08-01 00:00","url":"http://www.lieqi.com/read/44/131930/"},{"title":"揭秘：人临死前将会有的十三种神秘感受","picUrl":"http://img9.lieqi.com/upload1/thumb/28/131846.jpg","description":"猎奇奇闻","ctime":"2016-07-28 00:00","url":"http://www.lieqi.com/read/44/131846/"},{"title":"真假？据说这些地方就是通往冥界的入口","picUrl":"http://img9.lieqi.com/upload1/thumb/28/131848.jpg","description":"猎奇奇闻","ctime":"2016-07-28 00:00","url":"http://www.lieqi.com/read/44/131848/"},{"title":"轰动全国的北京地铁真实灵异怪事：胆小勿进","picUrl":"http://img9.lieqi.com/upload1/thumb/28/131855.jpg","description":"猎奇奇闻","ctime":"2016-07-28 00:00","url":"http://www.lieqi.com/read/44/131855/"},{"title":"1987陕西夜狸猫事件 村庄里人全消失","picUrl":"http://img9.lieqi.com/upload1/thumb/28/131800.jpg","description":"猎奇奇闻","ctime":"2016-07-27 00:00","url":"http://www.lieqi.com/read/44/131800/"},{"title":"不可思议：被斩掉头的无头公鸡竟活18个月","picUrl":"http://img9.lieqi.com/upload1/thumb/28/131735.jpg","description":"猎奇奇闻","ctime":"2016-07-26 00:00","url":"http://www.lieqi.com/read/44/131735/"}]
         * code : 200
         * msg : success
         */

        private int code;
        private String msg;
        private List<AnedoteItem> newslist;

        public static ShowapiResBodyBean objectFromData(String str) {

            return new Gson().fromJson(str, ShowapiResBodyBean.class);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<AnedoteItem> getNewslist() {
            return newslist;
        }

        public void setNewslist(List<AnedoteItem> newslist) {
            this.newslist = newslist;
        }

    }
}
