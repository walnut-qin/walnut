package com.kaos.skynet.api.data.his.entity.xyhis;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.skynet.api.data.his.entity.xyhis.DawnOrgDept.DeptOwnEnum;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.enums.SexEnum;
import com.kaos.skynet.core.util.ObjectUtils;
import com.kaos.skynet.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("XYHIS.FIN_IPR_INMAININFO")
public class FinIprInMainInfo {
    /**
     *
    */
    @TableId("INPATIENT_NO")
    private String inpatientNo;

    /**
     *
    */
    @TableField("MEDICAL_TYPE")
    private String medicalType;

    /**
     * 住院号
     */
    @TableField("PATIENT_NO")
    private String patientNo;

    /**
     * 就诊卡号
     */
    @TableField("CARD_NO")
    private String cardNo;

    /**
     * 社保卡号
     */
    @TableField("MCARD_NO")
    private String medCardNo;

    /**
     * 姓名
     */
    @TableField("NAME")
    private String name;

    /**
     * 性别
     */
    @TableField("SEX_CODE")
    private SexEnum sex;

    /**
     * 身份证号
     */
    @TableField("IDENNO")
    private String idenNo;

    /**
     * 拼写码
     */
    @TableField("SPELL_CODE")
    private String spellCode;

    /**
     * 生日
     */
    @TableField("BIRTHDAY")
    private LocalDateTime birthday;

    /**
     * 职业代码
     */
    @TableField("PROF_CODE")
    private String profCode;

    /**
     * 工作单位
     */
    @TableField("WORK_NAME")
    private String workName;

    /**
     * 工作电话
     */
    @TableField("WORK_TEL")
    private String workTel;

    /**
     * 工作邮编
     */
    @TableField("WORK_ZIP")
    private String workZip;

    /**
     * 家庭住址
     */
    @TableField("HOME")
    private String home;

    /**
     * 家庭电话
     */
    @TableField("HOME_TEL")
    private String homeTel;

    /**
     * 家庭邮编
     */
    @TableField("HOME_ZIP")
    private String homeZip;

    /**
     * 籍贯
     */
    @TableField("DIST")
    private String dist;

    /**
     * 出生地代码
     */
    @TableField("BIRTH_AREA")
    private String birthArea;

    /**
     * 民族
     */
    @TableField("NATION_CODE")
    private String nationCode;

    /**
     * 联系人姓名
     */
    @TableField("LINKMAN_NAME")
    private String linkmanName;

    /**
     * 联系人电话
     */
    @TableField("LINKMAN_TEL")
    private String linkmanTel;

    /**
     * 联系人地址
     */
    @TableField("LINKMAN_ADD")
    private String linkmanAddress;

    /**
     * 联系人关系
     */
    @TableField("RELA_CODE")
    private String linkmanRelation;

    /**
     * 婚姻状况
     */
    @TableField("MARI")
    private String mari;

    /**
     * 国籍
     */
    @TableField("COUN_CODE")
    private String country;

    /**
     * 身高
     */
    @TableField("HEIGHT")
    private Double height;

    /**
     * 体重
     */
    @TableField("WEIGHT")
    private Double weight;

    /**
     * 血压
     */
    @TableField("BLOOD_DRESS")
    private String bloodDress;

    /**
     * 重大疾病编码
     */
    @TableField("HEPATITIS_FLAG")
    private Boolean hepatitisFlag;

    /**
     * 过敏标志
     */
    @TableField("ANAPHY_FLAG")
    private Boolean anaphyFlag;

    /**
     * 入院日期
     */
    @TableField("IN_DATE")
    private LocalDateTime inDate;

    /**
     * 科室代码
     */
    @TableField("DEPT_CODE")
    private String deptCode;

    /**
     * 科室名称
     */
    @TableField("PACT_NAME")
    private String deptName;

    /**
     * 结算类型
     */
    @TableField("PAYKIND_CODE")
    private PayKindEnum payKind;

    /**
     * 合同单位编码
     */
    @TableField("PACT_CODE")
    private String pactCode;

    /**
     * 合同单位名
     */
    @TableField("PACT_NAME")
    private String pactName;

    /**
     * 床位号
     */
    @TableField("BED_NO")
    private String bedNo;

    /**
     * 病区编码
     */
    @TableField("NURSE_CELL_CODE")
    private String nurseCellCode;

    /**
     * 病区名称
     */
    @TableField("NURSE_CELL_NAME")
    private String nurseCellName;

    /**
     * 住院医师代码
     */
    @TableField("HOUSE_DOC_CODE")
    private String houseDocCode;

    /**
     * 住院医师姓名
     */
    @TableField("HOUSE_DOC_NAME")
    private String houseDocName;

    /**
     * 主治医师代码
     */
    @TableField("CHARGE_DOC_CODE")
    private String chargeDocCode;

    /**
     * 主治医师姓名
     */
    @TableField("CHARGE_DOC_NAME")
    private String chargeDocName;

    /**
     * 主任医师代码
     */
    @TableField("CHIEF_DOC_CODE")
    private String chiefDocCode;

    /**
     * 主任医师姓名
     */
    @TableField("CHIEF_DOC_NAME")
    private String chiefDocName;

    /**
     * 责任护士编码
     */
    @TableField("DUTY_NURSE_CODE")
    private String dutyNurseCode;

    /**
     * 责任护士姓名
     */
    @TableField("DUTY_NURSE_NAME")
    private String dutyNurseName;

    /**
     * 入院情况
     */
    @TableField("IN_CIRCS")
    private InCircsEnum inCircs;

    /**
     * 入院途径
     */
    @TableField("IN_AVENUE")
    private InAvenueEnum inAvenue;

    /**
     * 入院来源
     */
    @TableField("IN_SOURCE")
    private InSourceEnum inSourceEnum;

    /**
     * 入院次数
     */
    @TableField("IN_TIMES")
    private Integer inTimes;

    /**
     * 预交金额(未结)
     */
    @TableField("PREPAY_COST")
    private Double prepayCost;

    /**
     * 转入预交金额（未结)
     */
    @TableField("CHANGE_PREPAYCOST")
    private Double changePrepayCost;

    /**
     * 警戒线
     */
    @TableField("MONEY_ALERT")
    private Double moneyAlert;

    /**
     * 总费用
     */
    @TableField("TOT_COST")
    private Double totCost;

    /**
     * 自费金额
     */
    @TableField("OWN_COST")
    private Double ownCost;

    /**
     * 自付金额(未结)
     */
    @TableField("PAY_COST")
    private Double payCost;

    /**
     * 统筹金额(未结)
     */
    @TableField("PUB_COST")
    private Double pubCost;

    /**
     * 优惠金额(未结)
     */
    @TableField("ECO_COST")
    private Double ecoCost;

    /**
     * 余额(未结)
     */
    @TableField("FREE_COST")
    private Double feeCost;

    /**
     * 转入费用金额(未结)
     */
    @TableField("CHANGE_TOTCOST")
    private Double changeTotCost;

    /**
     * 待遇上限
     */
    @TableField("UPPER_LIMIT")
    private Double upperLimit;

    /**
     * 固定费用间隔天数
     */
    @TableField("FEE_INTERVAL")
    private Integer feeInterval;

    /**
     * 结算序号
     */
    @TableField("BALANCE_NO")
    private Integer balanceNo;

    /**
     * 费用金额(已结)
     */
    @TableField("BALANCE_COST")
    private Double balanceCost;

    /**
     * 预交金额(已结)
     */
    @TableField("BALANCE_PREPAY")
    private Double balancePrepay;

    /**
     * 结算日期
     */
    @TableField("BALANCE_DATE")
    private LocalDateTime balanceDate;

    /**
     * 是否关账
     */
    @TableField("STOP_ACOUNT")
    private Boolean stopAccount;

    /**
     * 是否为婴儿
     */
    @TableField("BABY_FLAG")
    private Boolean babyFlag;

    /**
     * 病案状态: 0 无需病案 1 需要病案 2 医生站形成病案 3 病案室形成病案 4病案封存
     */
    @TableField("CASE_FLAG")
    private String caseFlag;

    /**
     * 在院状态
     */
    @TableField("IN_STATE")
    private InStateEnum inState;

    /**
     * 是否请假
     */
    @TableField("LEAVE_FLAG")
    private Boolean leaveFlag;

    /**
     * 出院日期(预约)
     */
    @TableField("PREPAY_OUTDATE")
    private LocalDateTime prepayOutDate;

    /**
     *
    */
    @TableField("OUT_DATE")
    private LocalDateTime outDate;

    /**
     * 转归代号
     */
    @TableField("ZG")
    private String zg;

    /**
     * 开据医师
     */
    @TableField("EMPL_CODE")
    private String emplCode;

    /**
     * 是否在ICU
     */
    @TableField("IN_ICU")
    private Boolean inICU;

    /**
     * 病案送入病案室否0未1送
     */
    @TableField("CASESEND_FLAG")
    private Boolean caseSendFlag;

    /**
     * 护理级别(TEND):名称显示护理级别名称(一级护理，二级护理，三级护理)
     */
    @TableField("TEND")
    private String tend;

    /**
     * 病危：0 普通 1 病重 2 病危
     */
    @TableField("CRITICAL_FLAG")
    private String criticalFlag;

    /**
     * 上次固定费用时间
     */
    @TableField("PREFIXFEE_DATE")
    private LocalDateTime prefixFeeDate;

    /**
     * 操作员
     */
    @TableField("OPER_CODE")
    private String operCode;

    /**
     *
    */
    @TableField("OPER_DATE")
    private LocalDateTime operDate;

    /**
     * 血滞纳金
     */
    @TableField("BLOOD_LATEFEE")
    private Double bloodLateFee;

    /**
     * 公费患者日限额
     */
    @TableField("DAY_LIMIT")
    private Double dayLimit;

    /**
     * 公费患者日限额累计
     */
    @TableField("LIMIT_TOT")
    private Double limitTot;

    /**
     * 公费患者日限额超标部分金额
     */
    @TableField("LIMIT_OVERTOP")
    private Double limitOverTop;

    /**
     * 门诊诊断
     */
    @TableField("CLINIC_DIAGNOSE")
    private String clinicDiagnose;

    /**
     * 生育保险患者电脑号(医保用作欠费标记1为欠费)
     */
    @TableField("PROCREATE_PCNO")
    private String procreatePcNo;

    /**
     * 饮食
     */
    @TableField("DIETETIC_MARK")
    private String dieteticMark;

    /**
     * 公费患者公费药品累计(日限额)
     */
    @TableField("BURSARY_TOTMEDFEE")
    private Double bursaryTotMedFee;

    /**
     * 备注
     */
    @TableField("MEMO")
    private String memo;

    /**
     * 床位上限
     */
    @TableField("BED_LIMIT")
    private Double bedLimit;

    /**
     * 空调上限
     */
    @TableField("AIR_LIMIT")
    private Double airLimit;

    /**
     * 床费超标处理 0超标不限 1超标自理 2超标不计
     */
    @TableField("BEDOVERDEAL")
    private String bedOverDeal;

    /**
     * 扩展标记[无费入院标记]
     */
    @TableField("EXT_FLAG")
    private String extFlag;

    /**
     * 扩展标记1
     */
    @TableField("EXT_FLAG1")
    private String extFlag1;

    /**
     * 扩展标记2(襄阳保存特殊自费类型:火灾，弃婴，车祸等)
     */
    @TableField("EXT_FLAG2")
    private String extFlag2;

    /**
     * 膳食花费总额
     */
    @TableField("BOARD_COST")
    private Double boardCost;

    /**
     * 膳食预交金额
     */
    @TableField("BOARD_PREPAY")
    private Double boardPrepay;

    /**
     * 膳食结算状态：0在院 1出院
     */
    @TableField("BOARD_STATE")
    private String boardState;

    /**
     * 自费比例
     */
    @TableField("OWN_RATE")
    private Double ownRate;

    /**
     * 自付比例
     */
    @TableField("PAY_RATE")
    private Double payRate;

    /**
     * 扩展数值（中山一用作－剩余统筹金额）
     */
    @TableField("EXT_NUMBER")
    private Double extNumber;

    /**
     * 扩展编码（襄阳保存医保特殊记录：15r重复住院等）
     */
    @TableField("EXT_CODE")
    private String extCode;

    /**
     * 诊断名称（建议用此保存主诊断）
     */
    @TableField("DIAG_NAME")
    private String diagName;

    /**
     * 是否加密
     */
    @TableField("IS_ENCRYPTNAME")
    private Boolean isEncryptName;

    /**
     * 密文
     */
    @TableField("NORMALNAME")
    private String normalName;

    /**
     * 证件类型
     */
    @TableField("IDCARDTYPE")
    private String idCardType;

    /**
     * M 金额 D时间段
     */
    @TableField("ALTER_TYPE")
    private String alterType;

    /**
     * 警戒线开始时间
     */
    @TableField("ALTER_BEGIN")
    private LocalDateTime alterBegin;

    /**
     * 警戒线结束时间
     */
    @TableField("ALTER_END")
    private LocalDateTime alterEnd;

    /**
     * 警戒线批准人
     */
    @TableField("ALTER_APPROVE_CODE")
    private String alterApproveCode;

    /**
     * 警戒线批准原因
     */
    @TableField("ALTER_APPROVE_DATE")
    private LocalDateTime alterApproveDate;

    /**
     * 人民医院旧系统资格证书编号(导数据用)[襄阳暂存质控护士编码]
     */
    @TableField("OLDSI_NO")
    private String oldSiNo;

    /**
     * 患者医保审核确认标记
     */
    @TableField("LOCAL_EXT_FLAG1")
    private String localExtFlag1;

    /**
     * 医保普通备注
     */
    @TableField("LOCAL_EXT_FLAG2")
    private String localExtFlag2;

    /**
     * EMR住院流水号 SEQ_FIN_INPATIENTID
     */
    @TableField("EMR_INPATIENTID")
    private String emrInpatientId;

    /**
     * 是否是单病种患者0：非单病种1：单病种
     */
    @TableField("IS_SINGLEDIS")
    private Boolean isSingleDis;

    /**
     * 单病种ICD编码
     */
    @TableField("SINGLEDIS_ID")
    private String singleDisId;

    /**
     * 定额结算手术名称
     */
    @TableField("DISEASENAME")
    private String diseaseName;

    /**
     * 定额结算手术定额
     */
    @TableField("DISEASECOST")
    private Double diseaseCost;

    /**
     * 材料费用
     */
    @TableField("MATERIALCOST")
    private String materialCost;

    /**
     * 职员院区标识 0 全院 1 南区, 2北区 3东区
     */
    @TableField("DEPTOWN")
    private DeptOwnEnum deptOwn;

    /**
     * 急诊绿色通道结账
     */
    @TableField("IS_GREEN_BALANCE")
    private Boolean isGreenBalance;

    /**
     * 跨省医保省编码
     */
    @TableField("SI_PROVINCCODE")
    private String siProvinceCode;

    /**
     * 跨省医保省名称
     */
    @TableField("SI_PROVINCNAME")
    private String siProvinceName;

    /**
     * 跨省医保市编码
     */
    @TableField("SI_CITYCODE")
    private String siCityCode;

    /**
     * 跨省医保市名称
     */
    @TableField("SI_CITYNAME")
    private String siCityName;

    /**
     * 医保特殊备注
     */
    @TableField("LOCAL_EXT_FLAG3")
    private String localExtFlag3;

    /**
     * 是否是GCP患者，空值表示不是GCP患者，0取消GCP患者，1设置GCP患者。
     */
    @TableField("GCPINPATIENT")
    private Boolean gcpInpatient;

    /**
     * 绿色通道标志 1是 0不是
     */
    @TableField("GREENROAD")
    private Boolean greenRoad;

    /**
     * VIP患者标志 1是 0不是
     */
    @TableField("VIP_FLAG")
    private Boolean vipFlag;

    /**
     * 是否是ERAS患者，空值表示不是ERAS患者，0取消ERAS患者，1设置ERAS患者。
     */
    @TableField("ERASINPATIENT")
    private Boolean erasInpatient;

    /**
     * VTE等级
     */
    @TableField("VTE")
    private String vte;

    /**
     * VTE等级是否被医生确认：0 未确认；1 已确认
     */
    @TableField("VTE_CFM")
    private Boolean vteCfm;

    /**
     * 门诊号
     */
    @TableField("CLINIC_CODE")
    private String clinicCode;

    /**
     * 住院预约发生序号
     */
    @TableField("HAPPEN_NO")
    private Integer happenNo;

    /**
     * 医疗照顾人员类别
     */
    @TableField("MEDICAL_STAFF")
    private String medicalStaff;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 
     */
    @TableField("CASE_APPROVE_FLAG")
    private String caseApproveFlag;

    /**
     *
     */
    @TableField("CASE_REASON")
    private String caseReason;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinIprInMainInfo) {
            var that = (FinIprInMainInfo) arg0;
            return StringUtils.equals(this.inpatientNo, that.inpatientNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(inpatientNo);
    }

    @Getter
    @AllArgsConstructor
    public enum InStateEnum implements Enum {
        住院登记("R", "住院登记"),
        病房接诊("I", "病房接诊"),
        出院登记("B", "出院登记"),
        出院结算("O", "出院结算"),
        预约出院("P", "预约出院"),
        无费退院("N", "无费退院");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }

    /**
     * 枚举：入院来源
     */
    @Getter
    @AllArgsConstructor
    public enum InSourceEnum implements Enum {
        门诊("1", "门诊"), 急诊("2", "急诊"), 转科("3", "转科"), 转院("4", "转院");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }

    /**
     * 入院情况 {@code COM_DICTIONARY#TYPE = INCIRCS}
     */
    @Getter
    @AllArgsConstructor
    public enum InCircsEnum implements Enum {
        一般("1", "一般"), 急("2", "急"), 危("3", "危");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }

    @Getter
    @AllArgsConstructor
    public enum InAvenueEnum implements Enum {
        本市("1", "本市"), 市郊("2", "市郊"), 市外("3", "市外"), 省内("4", "省内"), 省外("5", "省外"), 境外("6", "境外");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }

    /**
     * 结算类别代码 {@code COM_DICTIONARY#TYPE = PAYKIND}
     */
    @Getter
    @AllArgsConstructor
    public enum PayKindEnum implements Enum {
        自费("01", "自费"), 医保("02", "医保"), 公费("03", "公费"), 特约单位("04", "特约单位"), 本院职工("05", "本院职工");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }
}
