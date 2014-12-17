var DateUtil = function() {
	/**
	 * ��õ�ǰ����
	 */
	this.getCurrentDate = function() {
		return new Date();
	};

	/**
	 * �����������
	 */
	this.getYestodayDate = function() {
		var myDate = this.getCurrentDate();
		myDate.setDate(myDate.getDate() - 1);
		return myDate;
	};
	/**
	 * ���ǰn�������
	 */
	this.getPreviousDate = function(n) {
		var myDate = this.getCurrentDate();
		myDate.setDate(myDate.getDate() - Integer.parseInt(n));
		return myDate;
	};
	/**
	 * ���ǰn�������
	 * 
	 *
	 */
	this.getPreviousDate = function(date ,n) {
		var year=date.substring(0,4);
		var month=date.substring(4,6);
		var day=date.substring(6,8);
		
		// ���Ϊ0����,�Ǳ���ĵ�һ��,���Բ��ܼ�
		if (month == 0) {
			month = 11;// �·�Ϊ���������·�
			year--;// ��ݼ�1
			return new Date(year, month, 1);
		}
		// ����,ֻ��ȥ�·�
		month--;
		return new Date(year, month, 1);
	};
	/**
	 * ��ñ�����ֹ����
	 */
	this.getCurrentWeek = function() {
		// ��ֹ��������
		var startStop = new Array();
		// ��ȡ��ǰʱ��
		var currentDate = this.getCurrentDate();
		// ����date��һ���е�ĳһ��
		var week = currentDate.getDay();
		// ����date��һ�����е�ĳһ��
		var month = currentDate.getDate();
		// һ��ĺ�����
		var millisecond = 1000 * 60 * 60 * 24;
		// ��ȥ������
		var minusDay = week != 0 ? week - 1 : 6;
		// alert(minusDay);
		// ���� ��һ
		var monday = new Date(currentDate.getTime() - (minusDay * millisecond));
		// ���� ����
		var sunday = new Date(monday.getTime() + (6 * millisecond));
		// ��ӱ���ʱ��
		startStop.push(monday);// ������ʼʱ��
		// ��ӱ������һ��ʱ��
		startStop.push(sunday);// ������ֹʱ��
		// ����
		return startStop;
	};

	/**
	 * ��ñ��µ���ֹ����
	 */
	this.getCurrentMonth = function() {
		// ��ֹ��������
		var startStop = new Array();
		// ��ȡ��ǰʱ��
		var currentDate = this.getCurrentDate();
		// ��õ�ǰ�·�0-11
		var currentMonth = currentDate.getMonth();
		// ��õ�ǰ���4λ��
		var currentYear = currentDate.getFullYear();
		// ������µ�һ��
		var firstDay = new Date(currentYear, currentMonth, 1);
		// ��Ϊ12�µ�ʱ�������Ҫ��1
		// �·���Ҫ����Ϊ0 Ҳ������һ��ĵ�һ����
		if (currentMonth == 11) {
			currentYear++;
			currentMonth = 0;// ��Ϊ
		} else {
			// ����ֻ���·�����,�Ա������һ�µĵ�һ��
			currentMonth++;
		}
		// һ��ĺ�����
		var millisecond = 1000 * 60 * 60 * 24;
		// ���µĵ�һ��
		var nextMonthDayOne = new Date(currentYear, currentMonth, 1);
		// ������µ����һ��
		var lastDay = new Date(nextMonthDayOne.getTime() - millisecond);
		// ����������з���
		startStop.push(firstDay);
		startStop.push(lastDay);
		// ����
		return startStop;
	};

	/**
	 * ��ñ����ȵ���ֹ����
	 */
	this.getCurrentSeason = function() {
		// ��ֹ��������
		var startStop = new Array();
		// ��ȡ��ǰʱ��
		var currentDate = this.getCurrentDate();
		// ��õ�ǰ�·�0-11
		var currentMonth = currentDate.getMonth();
		// ��õ�ǰ���4λ��
		var currentYear = currentDate.getFullYear();
		// ��ñ����ȿ�ʼ�·�
		var quarterSeasonStartMonth = this
				.getQuarterSeasonStartMonth(currentMonth);
		// ��ñ����Ƚ����·�
		var quarterSeasonEndMonth = quarterSeasonStartMonth + 2;

		// ��ñ����ȿ�ʼ������
		var quarterSeasonStartDate = new Date(currentYear,
				quarterSeasonStartMonth, 1);
		// ��ñ����Ƚ���������
		var quarterSeasonEndDate = new Date(currentYear, quarterSeasonEndMonth,
				this.getMonthDays(currentYear, quarterSeasonEndMonth));
		// �������鷵��
		startStop.push(quarterSeasonStartDate);
		startStop.push(quarterSeasonEndDate);
		// ����
		return startStop;
	};

	/**
	 * �õ��������ֹ����
	 */
	this.getCurrentYear = function() {
		// ��ֹ��������
		var startStop = new Array();
		// ��ȡ��ǰʱ��
		var currentDate = this.getCurrentDate();
		// ��õ�ǰ���4λ��
		var currentYear = currentDate.getFullYear();
		// �����һ��
		var currentYearFirstDate = new Date(currentYear, 0, 1);
		// �������һ��
		var currentYearLastDate = new Date(currentYear, 11, 31);
		// ���������
		startStop.push(currentYearFirstDate);
		startStop.push(currentYearLastDate);
		// ����
		return startStop;
	};

	/**
	 * �����һ�ܵ���ֹ����
	 */
	this.getPreviousWeek = function() {
		// ��ֹ��������
		var startStop = new Array();
		// ��ȡ��ǰʱ��
		var currentDate = this.getCurrentDate();
		// ����date��һ���е�ĳһ��
		var week = currentDate.getDay();
		// ����date��һ�����е�ĳһ��
		var month = currentDate.getDate();
		// һ��ĺ�����
		var millisecond = 1000 * 60 * 60 * 24;
		// ��ȥ������
		var minusDay = week != 0 ? week - 1 : 6;
		// ��õ�ǰ�ܵĵ�һ��
		var currentWeekDayOne = new Date(currentDate.getTime()
				- (millisecond * minusDay));
		// �������һ�켴���ܿ�ʼ��ǰһ��
		var priorWeekLastDay = new Date(currentWeekDayOne.getTime()
				- millisecond);
		// ���ܵĵ�һ��
		var priorWeekFirstDay = new Date(priorWeekLastDay.getTime()
				- (millisecond * 6));
		// ���������
		startStop.push(priorWeekFirstDay);
		startStop.push(priorWeekLastDay);
		return startStop;
	};

	/**
	 * �����һ�µ���ֹ����
	 */
	this.getPreviousMonth = function() {
		// ��ֹ��������
		var startStop = new Array();
		// ��ȡ��ǰʱ��
		var currentDate = this.getCurrentDate();
		// ��õ�ǰ�·�0-11
		var currentMonth = currentDate.getMonth();
		// ��õ�ǰ���4λ��
		var currentYear = currentDate.getFullYear();
		// �����һ���µĵ�һ��
		var priorMonthFirstDay = this.getPriorMonthFirstDay(currentYear,
				currentMonth);
		// �����һ�µ����һ��
		var priorMonthLastDay = new Date(priorMonthFirstDay.getFullYear(),
				priorMonthFirstDay.getMonth(), this.getMonthDays(
						priorMonthFirstDay.getFullYear(), priorMonthFirstDay
								.getMonth()));
		// ���������
		startStop.push(priorMonthFirstDay);
		startStop.push(priorMonthLastDay);
		// ����
		return startStop;
	};

	/**
	 * �õ��ϼ��ȵ���ֹ����
	 */
	this.getPreviousSeason = function() {
		// ��ֹ��������
		var startStop = new Array();
		// ��ȡ��ǰʱ��
		var currentDate = this.getCurrentDate();
		// ��õ�ǰ�·�0-11
		var currentMonth = currentDate.getMonth();
		// ��õ�ǰ���4λ��
		var currentYear = currentDate.getFullYear();
		// �ϼ��ȵĵ�һ��
		var priorSeasonFirstDay = this.getPriorSeasonFirstDay(currentYear,
				currentMonth);
		// �ϼ��ȵ����һ��
		var priorSeasonLastDay = new Date(priorSeasonFirstDay.getFullYear(),
				priorSeasonFirstDay.getMonth() + 2, this.getMonthDays(
						priorSeasonFirstDay.getFullYear(), priorSeasonFirstDay
								.getMonth() + 2));
		// ���������
		startStop.push(priorSeasonFirstDay);
		startStop.push(priorSeasonLastDay);
		return startStop;
	};

	/**
	 * �õ�ȥ�����ֹ����
	 */
	this.getPreviousYear = function() {
		// ��ֹ��������
		var startStop = new Array();
		// ��ȡ��ǰʱ��
		var currentDate = this.getCurrentDate();
		// ��õ�ǰ���4λ��
		var currentYear = currentDate.getFullYear();
		currentYear--;
		var priorYearFirstDay = new Date(currentYear, 0, 1);
		var priorYearLastDay = new Date(currentYear, 11, 1);
		// ���������
		startStop.push(priorYearFirstDay);
		startStop.push(priorYearLastDay);
		return startStop;
	};

	/**
	 * �õ������ȿ�ʼ���·�
	 * 
	 * @param month
	 *            ��Ҫ������·�
	 */
	this.getQuarterSeasonStartMonth = function(month) {
		var quarterMonthStart = 0;
		var spring = 0; // ��
		var summer = 3; // ��
		var fall = 6; // ��
		var winter = 9;// ��
		// �·ݴ�0-11
		if (month < 3) {
			return spring;
		}
		if (month < 6) {
			return summer;
		}
		if (month < 9) {
			return fall;
		}
		return winter;
	};

	/**
	 * ��ø��µ�����
	 * 
	 * @param year
	 *            ���
	 * @param month
	 *            �·�
	 */
	this.getMonthDays = function(year, month) {
		// ���µ�һ�� 1-31
		var relativeDate = new Date(year, month, 1);
		// ��õ�ǰ�·�0-11
		var relativeMonth = relativeDate.getMonth();
		// ��õ�ǰ���4λ��
		var relativeYear = relativeDate.getFullYear();
		// ��Ϊ12�µ�ʱ�������Ҫ��1
		// �·���Ҫ����Ϊ0 Ҳ������һ��ĵ�һ����
		if (relativeMonth == 11) {
			relativeYear++;
			relativeMonth = 0;
		} else {
			// ����ֻ���·�����,�Ա������һ�µĵ�һ��
			relativeMonth++;
		}
		// һ��ĺ�����
		var millisecond = 1000 * 60 * 60 * 24;
		// ���µĵ�һ��
		var nextMonthDayOne = new Date(relativeYear, relativeMonth, 1);
		// ���صõ����µ����һ��,Ҳ���Ǳ���������
		return new Date(nextMonthDayOne.getTime() - millisecond).getDate();
	};

	/**
	 * ������һ���µĵ�һ��
	 * 
	 * @param year
	 *            ��
	 * @param month
	 *            ��
	 */
	this.getPriorMonthFirstDay = function(year, month) {
		// ���Ϊ0����,�Ǳ���ĵ�һ��,���Բ��ܼ�
		if (month == 0) {
			month = 11;// �·�Ϊ���������·�
			year--;// ��ݼ�1
			return new Date(year, month, 1);
		}
		// ����,ֻ��ȥ�·�
		month--;
		return new Date(year, month, 1);
	};

	/**
	 * �õ��ϼ��ȵĵ�һ��
	 * 
	 * @param year
	 *            �����Ӧ���������õ��ĵ�ǰ�����ȵ����
	 * @param month
	 *            ���Ӧ���������õ��ĵ�ǰ���ȵĿ�ʼ�·�
	 */
	this.getPriorSeasonFirstDay = function(year, month) {
		var quarterMonthStart = 0;
		var spring = 0; // ��
		var summer = 3; // ��
		var fall = 6; // ��
		var winter = 9;// ��
		// �·ݴ�0-11
		switch (month) {// ���ȵ���ʵ�·�
		case spring:
			// ����ǵ�һ������Ӧ�õ�ȥ��Ķ���
			year--;
			month = winter;
			break;
		case summer:
			month = spring;
			break;
		case fall:
			month = summer;
			break;
		case winter:
			month = fall;
			break;
		}
		;
		return new Date(year, month, 1);
	};
			/**
	 * ����������
	 * 
	 * @date1
	 *            ��ʼ����
	 * @date2
	 *            ��������
	 */
	this.getNumDays = function(date1, date2) {
		var d1=date1.substring(0,4);
		var d2=date1.substring(4,6);
		var d3=date1.substring(6,8);
		var oDate1 = new Date(d2 + '-' + d3 + '-' +d1)   //ת��Ϊ12-23-2008��ʽ
		
		var d11=date2.substring(0,4);
		var d22=date2.substring(4,6);
		var d33=date2.substring(6,8);
		var oDate2 = new Date(d22 + '-' + d33 + '-' +d11)   //ת��Ϊ12-25-2008��ʽ
		
		var idays=parseInt(Math.abs(oDate2-oDate1)/1000/60/60/24);//�����ĺ�����ת��Ϊ����
		return idays;
	};
	this.getNumDays2 = function(date1, date2) {
		var d1=date1.substring(0,4);
		var d2=date1.substring(4,6);
		var d3=date1.substring(6,8);
		var oDate1 = new Date(d2 + '-' + d3 + '-' +d1)   //ת��Ϊ12-23-2008��ʽ
		
		var d11=date2.substring(0,4);
		var d22=date2.substring(4,6);
		var d33=date2.substring(6,8);
		var oDate2 = new Date(d22 + '-' + d33 + '-' +d11)   //ת��Ϊ12-25-2008��ʽ
		
		var idays=parseInt(Math.abs(oDate2-oDate1)/1000/60/60/24);//�����ĺ�����ת��Ϊ����
		return idays;
	};
};