package com.egomogo.scraper.shell.appservice

interface ScarperService {

    /**
     * DB에서 메뉴가 없는 매장을 조회한 뒤, <br>
     * 해당 매장들을 카카오 플레이스에서 스크래핑하고, <br>
     * 스크래핑된 매장의 메뉴들을 저장하는 메소드. <br>
     * <br>
     * @return 스크래핑 성공한 매장 수
     */
    fun saveScarpedMenuResult(): Int

}