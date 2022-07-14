package com.kaos.skynet.api.data.his.entity.xyhis;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.skynet.api.data.his.enums.ValidEnum;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.enums.SexEnum;
import com.kaos.skynet.core.util.ObjectUtils;
import com.kaos.skynet.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("XYHIS.COM_PATIENTINFO")
public class ComPatientInfo {
    /**
    *
    **/
    @TableId("CARD_NO")
    private String cardNo;

    /**
     *
     **/
    @TableField("IC_CARDNO")
    private String icCardNo;

    /**
     *
     **/
    @TableField("NAME")
    private String name;

    /**
     *
     **/
    @TableField("SPELL_CODE")
    private String spellCode;

    /**
     *
     **/
    @TableField("WB_CODE")
    private String wbCode;

    /**
     *
     **/
    @TableField("BIRTHDAY")
    private LocalDateTime birthday;

    /**
     *
     **/
    @TableField("SEX_CODE")
    private SexEnum sex;

    /**
     *
     **/
    @TableField("IDENNO")
    private String idenNo;

    /**
     *
     **/
    @TableField("PROF_CODE")
    private String profCode;

    /**
     *
     **/
    @TableField("WORK_HOME")
    private String workHome;

    /**
     *
     **/
    @TableField("WORK_TEL")
    private String workTel;

    /**
     *
     **/
    @TableField("WORK_ZIP")
    private String workZip;

    /**
     *
     **/
    @TableField("HOME")
    private String home;

    /**
     *
     **/
    @TableField("HOME_TEL")
    private String homeTel;

    /**
     *
     **/
    @TableField("HOME_ZIP")
    private String homeZip;

    /**
     *
     **/
    @TableField("DISTRICT")
    private String district;

    /**
     *
     **/
    @TableField("NATION_CODE")
    private String nationCode;

    /**
     *
     **/
    @TableField("LINKMAN_NAME")
    private String linkmanName;

    /**
     *
     **/
    @TableField("LINKMAN_TEL")
    private String linkmanTel;

    /**
     *
     **/
    @TableField("LINKMAN_ADD")
    private String linkmanAddress;

    /**
     *
     **/
    @TableField("RELA_CODE")
    private String linkmanRelation;

    /**
     *
     **/
    @TableField("MARI")
    private String mari;

    /**
     *
     **/
    @TableField("COUN_CODE")
    private String country;

    /**
     *
     **/
    @TableField("PAYKIND_CODE")
    private String payKindCode;

    /**
     *
     **/
    @TableField("PAYKIND_NAME")
    private String payKindName;

    /**
     *
     **/
    @TableField("PACT_CODE")
    private String pactCode;

    /**
     *
     **/
    @TableField("PACT_NAME")
    private String pactName;

    /**
     *
     **/
    @TableField("MCARD_NO")
    private String medCardNo;

    /**
     *
     **/
    @TableField("AREA_CODE")
    private String areaCode;

    /**
     *
     **/
    @TableField("FRAMT")
    private String framt;

    /**
     *
     **/
    @TableField("ANAPHY_FLAG")
    private Boolean anaphyFlag;

    /**
     *
     **/
    @TableField("HEPATITIS_FLAG")
    private Boolean hepatitisFlag;

    /**
     *
     **/
    @TableField("ACT_CODE")
    private String passwd;

    /**
     *
     **/
    @TableField("ACT_AMT")
    private Double accountAmt;

    /**
     *
     **/
    @TableField("LACT_SUM")
    private Double lastSum;

    /**
     *
     **/
    @TableField("LBANK_SUM")
    private Double lastBankSum;

    /**
     *
     **/
    @TableField("ARREAR_TIMES")
    private Integer arrearTimes;

    /**
     *
     **/
    @TableField("ARREAR_SUM")
    private Double arrearSum;

    /**
     *
     **/
    @TableField("INHOS_SOURCE")
    private String inHosSource;

    /**
     *
     **/
    @TableField("LIHOS_DATE")
    private LocalDateTime lastInHosDate;

    /**
     *
     **/
    @TableField("INHOS_TIMES")
    private Integer inHosTimes;

    /**
     *
     **/
    @TableField("LOUTHOS_DATE")
    private LocalDateTime lastOutHosDate;

    /**
     *
     **/
    @TableField("FIR_SEE_DATE")
    private LocalDateTime firstSeeDate;

    /**
     *
     **/
    @TableField("LREG_DATE")
    private LocalDateTime lastRegDate;

    /**
     *
     **/
    @TableField("DISOBY_CNT")
    private Integer disobyCnt;

    /**
     *
     **/
    @TableField("END_DATE")
    private LocalDateTime endDate;

    /**
     *
     **/
    @TableField("MARK")
    private String mark;

    /**
     *
     **/
    @TableField("OPER_CODE")
    private String operCode;

    /**
     *
     **/
    @TableField("OPER_DATE")
    private LocalDateTime operDate;

    /**
     *
     **/
    @TableField("IS_VALID")
    private ValidEnum isValid;

    /**
     *
     **/
    @TableField("FEE_KIND")
    private String feeKind;

    /**
     *
     **/
    @TableField("OLD_CARDNO")
    private String oldCardNo;

    /**
     *
     **/
    @TableField("IS_ENCRYPTNAME")
    private Boolean isEncryptName;

    /**
     *
     **/
    @TableField("NORMALNAME")
    private String normalName;

    /**
     *
     **/
    @TableField("IDCARDTYPE")
    private String idCardType;

    /**
     *
     **/
    @TableField("VIP_FLAG")
    private Boolean vipFlag;

    /**
     *
     **/
    @TableField("MONTHER_NAME")
    private String motherName;

    /**
     *
     **/
    @TableField("IS_TREATMENT")
    private Boolean isTreatment;

    /**
     *
     **/
    @TableField("CASE_NO")
    private String caseNo;

    /**
     *
     **/
    @TableField("INSURANCE_ID")
    private String insuranceId;

    /**
     *
     **/
    @TableField("INSURANCE_NAME")
    private String insuranceName;

    /**
     *
     **/
    @TableField("HOME_DOOR_NO")
    private String homeDoorNo;

    /**
     *
     **/
    @TableField("LINKMAN_DOOR_NO")
    private String linkmanDoorNo;

    /**
     *
     **/
    @TableField("EMAIL")
    private String email;

    /**
     *
     **/
    @TableField("EMR_PATID")
    private String emrPatId;

    /**
     *
     **/
    @TableField("PATIENT_SOURCE")
    private String patientSource;

    /**
     *
     **/
    @TableField("GREENROAD")
    private Boolean greenRoad;

    /**
     *
     **/
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    /**
     *
     **/
    @TableField("IS_2019NCOV")
    private String is2019ncov;

    /**
     *
     **/
    @TableField("LINKMAN_IDENTYPE")
    private String linkmanIdenType;

    /**
     *
     **/
    @TableField("LINKMAN_IDENNO")
    private String linkmanIdenNo;

    /**
     *
     **/
    @TableField("IDEN_FLAG")
    private Boolean idenFlag;

    /**
     *
     **/
    @TableField("PROVINCE")
    private String province;

    /**
     *
     **/
    @TableField("CITY")
    private String city;

    /**
     *
     **/
    @TableField("AREA")
    private String area;

    /**
     *
     **/
    @TableField("VIP2_FLAG")
    private Boolean vip2Flag;

    /**
     *
     **/
    @TableField("REPRODUCTIVEFUND")
    private String reproductiveFund;

    /**
     *
     **/
    @TableField("ACCOMPANYCARDNO")
    private String accompanyCardNo;

    /**
     *
     **/
    @TableField("ISEMPLOYEE")
    private Boolean isEmpl;

    /**
     *
     **/
    @TableField("EMPI")
    private String empi;

    /**
     *
     **/
    @TableField("EMPL_CODE")
    private String emplCode;

    /**
     *
     **/
    @TableField("GCPOUTPATIENT")
    private Boolean gcpFlag;

    /**
     *
     **/
    @TableField("ROAD")
    private String road;

    /**
     *
     **/
    @TableField("COUNTRY")
    private String village;

    /**
     *
     **/
    @TableField("COUNTRYCITY")
    private String townShip;

    /**
     *
     **/
    @TableField("HEALTH_CODE")
    private HealthCodeEnum healthCode;

    /**
     *
     **/
    @TableField("TRAVEL_CODE")
    private TravelCodeEnum travelCode;

    /**
     *
     **/
    @TableField("HIGH_RISK_FLAG")
    private Boolean highRiskFlag;

    /**
     *
     **/
    @TableField("HIGH_RISK_AREA")
    private String highRiskArea;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof ComPatientInfo) {
            var that = (ComPatientInfo) arg0;
            return StringUtils.equals(this.cardNo, that.cardNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(cardNo);
    }

    @Getter
    @AllArgsConstructor
    public enum HealthCodeEnum implements Enum {
        绿码("0", "绿码"),
        黄码("1", "黄码"),
        红码("2", "红码");

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
    public enum TravelCodeEnum implements Enum {
        正常("0", "正常"), 带星号("1", "带星号"), 黄码("2", "黄码"), 红码("3", "红码");

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
