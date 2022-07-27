package com.kaos.walnut.api.data.his.entity.xyhis;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.api.data.his.entity.xyhis.DawnOrgDept.DeptOwnEnum;
import com.kaos.walnut.core.type.Enum;
import com.kaos.walnut.core.type.enums.SexEnum;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("XYHIS.MET_OPS_APPLY")
public class MetOpsApply {
    /**
     * 手术申请单号
     */
    @TableId("OPERATIONNO")
    String applyNo;

    /**
     * 门诊号/住院流水号
     */
    @TableField("CLINIC_CODE")
    String clinicCode;

    /**
     * 病案号/病历号
     */
    @TableField("PATIENT_NO")
    String patientNo;

    /**
     * 
     */
    @TableField("PASOURCE")
    PaSourceEnum paSource;

    /**
     * 患者姓名
     */
    @TableField("NAME")
    String name;

    /**
     * 患者性别
     */
    @TableField("SEX_CODE")
    SexEnum sex;

    /**
     * 生日
     */
    @TableField("BIRTHDAY")
    LocalDate birthday;

    /**
     * 预交金
     */
    @TableField("PREPAY_COST")
    Double prepayCost;

    /**
     * 住院科室
     */
    @TableField("DEPT_CODE")
    String deptCode;

    /**
     * 床号
     */
    @TableField("BED_NO")
    String bedNo;

    /**
     * 患者血型
     */
    @TableField("BLOOD_CODE")
    String bloodCode;

    /**
     * 诊断
     */
    @TableField("DIAGNOSE")
    String diagnosis;

    /**
     * 手术类型
     */
    @TableField("OPS_KIND")
    OpsKindEnum opsKind;

    /**
     * 手术医生编码
     */
    @TableField("OPS_DOCD")
    String opsDocCode;

    /**
     * 指导医生编码
     */
    @TableField("GUI_DOCD")
    String guiDocCode;

    /**
     * 病房号
     */
    @TableField("SICK_ROOM")
    String sickRoom;

    /**
     * 预约时间
     */
    @TableField("PRE_DATE")
    LocalDateTime preDate;

    /**
     * 预定用时
     */
    @TableField("DURATION")
    Integer duration;

    /**
     * 麻醉类型（具体的麻醉类型一般由麻醉师填写）
     */
    @TableField("ANES_TYPE")
    String anesType;

    /**
     * 助手数量
     */
    @TableField("HELPER_NUM")
    Integer helperNum;

    /**
     * 洗手护士数
     */
    @TableField("WASH_NURSE")
    Integer washNurse;

    /**
     * 随台护士数
     */
    @TableField("ACCO_NURSE")
    Integer accoNurse;

    /**
     * 巡回护士数
     */
    @TableField("PREP_NURSE")
    Integer prepNurse;

    /**
     * 执行科室
     */
    @TableField("EXEC_DEPT")
    String execDept;

    /**
     * 1普通 2加台 3点台 4 加急台
     */
    @TableField("CONSOLE_TYPE")
    String consoleType;

    /**
     * 申请医生编码
     */
    @TableField("APPLY_DOCD")
    String applyDocCode;

    /**
     * 申请科室编码
     */
    @TableField("APPLY_DPCD")
    String applyDeptCode;

    /**
     * 申请时间
     */
    @TableField("APPLY_DATE")
    LocalDateTime applyDate;

    /**
     * 备注
     */
    @TableField("APPLY_NOTE")
    String applyNote;

    /**
     * 审批医生编码
     */
    @TableField("APPR_DOCD")
    String apprDocCode;

    /**
     * 审批时间
     */
    @TableField("APPR_DATE")
    LocalDateTime apprDate;

    /**
     * 审批备注
     */
    @TableField("APPR_NOTE")
    String apprNote;

    /**
     * 麻醉医生编码
     */
    @TableField("ANES_DOCD")
    String anesDocCode;

    /**
     * 手术规模
     */
    @TableField("DEGREE")
    String degree;

    /**
     * 切口类型
     */
    @TableField("INCI_TYPE")
    String inciType;

    /**
     * 是否有菌
     */
    @TableField("YNGERM")
    Boolean ynGerm;

    /**
     * 1 幕上 2 幕下 该字段之前没用 由汪艳群改为 0非首台 1首台
     */
    @TableField("SCREENUP")
    String screenUp;

    /**
     * 手术台
     */
    @TableField("CONSOLE_CODE")
    String consoleCode;

    /**
     * 接患者时间
     */
    @TableField("RECEPT_DATE")
    LocalDateTime receptDate;

    /**
     * 是否允许医生查看安排结果 1 允许 2 不允许
     */
    @TableField("BLOOD_TYPE")
    String bloodType;

    /**
     * 血量[暂存检验结果]
     */
    @TableField("BLOOD_NUM")
    Integer bloodNum;

    /**
     * 用血单位
     */
    @TableField("BLOOD_UNIT")
    String bloodUnit;

    /**
     * 手术注意事项
     */
    @TableField("OPS_NOTE")
    String opsNote;

    /**
     * 麻醉注意事项
     */
    @TableField("ANE_NOTE")
    String anesNote;

    /**
     * 1手术申请 2 手术审批 3手术安排 4手术完成 5取消手术登记 6手术审批未通过
     */
    @TableField("EXECSTATUS")
    ExecStatusEnum execStatus;

    /**
     * 0未做手术/1已做手术
     */
    @TableField("YNFINISHED")
    Boolean ynFinished;

    /**
     * 0未麻醉/1已麻醉
     */
    @TableField("YNANESTH")
    Boolean ynAnes;

    /**
     * 签字家属 暂用为非计划手术标示 1非计划
     */
    @TableField("FOLK")
    Boolean unplan;

    /**
     * 家属关系 暂用为非计划手术是否已申请 1已申请
     */
    @TableField("RELA_CODE")
    Boolean unplanApplied;

    /**
     * 家属意见
     */
    @TableField("FOLK_COMMENT")
    String folkComment;

    /**
     * 加急手术,1是/0否
     */
    @TableField("YNURGENT")
    Boolean ynUrgent;

    /**
     * 是否已经计费 , 0 未收费,1 已计费
     */
    @TableField("YNCHANGE")
    Boolean ynCharge;

    /**
     * 1重症/0否
     */
    @TableField("YNHEAVY")
    Boolean ynHeavy;

    /**
     * 1特殊手术/0否
     */
    @TableField("YNSPECIAL")
    Boolean ynSpecial;

    /**
     * 操作员
     */
    @TableField("OPER_CODE")
    String operCode;

    /**
     * 操作时间
     */
    @TableField("OPER_DATE")
    LocalDateTime operDate;

    /**
     * 是否有效
     */
    @TableField("YNVALID")
    Boolean ynValid;

    /**
     * 1合并/0否
     */
    @TableField("YNUNITE")
    Boolean ynUnite;

    /**
     * 合并后手术序列号
     */
    @TableField("UNITE_NO")
    String uniteNo;

    /**
     * 是否需要随台护士 0是/1否
     */
    @TableField("ISNEEDACCO")
    Boolean needAcco;

    /**
     * 是否需要巡回护士 0是/1否
     */
    @TableField("ISNEEDPREP")
    Boolean needPrep;

    /**
     * 房间号
     */
    @TableField("ROOM_ID")
    String roomId;

    /**
     * 手术医生科室编码（医生可能会发生转科，所以此处记录当时医生科室）
     */
    @TableField("DOC_DPCD")
    String docDeptCode;

    /**
     * 麻醉类别（局麻或选麻，医生申请时填写）
     */
    @TableField("ANES_WAY")
    String anesWay;

    /**
     * 存储体位和部位，用|分开，前面代表部位，后面代表体位
     */
    @TableField("POSITION")
    String position;

    /**
     * 疾病病种
     */
    @TableField("ENEITY")
    String eneity;

    /**
     * 拟手术持续时间
     */
    @TableField("LASTTIME")
    String lastTime;

    /**
     * 是否隔离手术,1,是0,否
     */
    @TableField("ISOLATION")
    Boolean isolate;

    /**
     * 安排是否作废
     */
    @TableField("ISCANCEL")
    Boolean isCancel;

    /**
     * 是否进行麻醉安排 0.未安排1.已安排 2.安排作废
     */
    @TableField("ANESTHARRANGESTATE")
    String anesArrState;

    /**
     * 是否需要C形臂
     */
    @TableField("ISNEEDCARM")
    String isNeedCArm;

    /**
     * 参观人员
     */
    @TableField("VISITOR")
    String visitor;

    /**
     * 0.手术申请 1.麻醉申请
     */
    @TableField("APPLY_TYPE")
    String applyType;

    /**
     * 作废原因（手术已安排再作废需要填写作废原因）
     */
    @TableField("CANCLEREASON")
    String cancelReason;

    /**
     * 排序号 hangwq 2014.4.22
     */
    @TableField("SORT_NO")
    String sortNo;

    /**
     * 1为医生可以看到手术安排 其他则医生看不到
     */
    @TableField("SEEFLAG")
    Boolean seeFlag;

    /**
     * 是否非计划再次手术 1是 0否[[暂存手术名称备注]]
     */
    @TableField("AGAINPLAN")
    String surgeryNameNote;

    /**
     * 职员院区标识 0 全院 1 南区, 2北区 3东区
     */
    @TableField("DEPTOWN")
    DeptOwnEnum deptOwn;

    /**
     * 手术标识
     */
    @TableField("OPERMARK")
    String operMark;

    /**
     * 就诊卡号
     */
    @TableField("CARD_NO")
    String cardNo;

    /**
     * 门诊流水号日间申请专用
     */
    @TableField("CLINICCODE")
    String dailyClinicCode;

    /**
     * 是否申请术中冰冻标识
     */
    @TableField("FROZEN_FLAG")
    Boolean frozenFlag;

    /**
     * 
     */
    @TableField("FROZEN_BEGIN_DATE")
    LocalDateTime frozenBeginDate;

    /**
     * 
     */
    @TableField("FROZEN_END_DATE")
    LocalDateTime frozenEndDate;

    /**
     * 术后是否计划转入ICU
     */
    @TableField("ICU_FLAG")
    Boolean icuFlag;

    /**
     * 高值耗材（骨科专用）
     */
    @TableField("HIGH_VALUE_CONSUMABLES")
    String highValueConsumables;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MetOpsApply) {
            var that = (MetOpsApply) arg0;
            return StringUtils.equals(this.applyNo, that.applyNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(applyNo);
    }

    @Getter
    @AllArgsConstructor
    public enum PaSourceEnum implements Enum {
        门诊("1", "门诊"),
        住院("2", "住院");

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
    public enum OpsKindEnum implements Enum {
        普通("1", "普通"),
        急诊("2", "急诊"),
        日间("3", "日间"),
        加台("7", "加台"),
        绿通("8", "绿通");

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
    public enum ExecStatusEnum implements Enum {
        手术申请("1", "手术申请"),
        手术审批("2", "手术审批"),
        手术安排("3", "手术安排"),
        手术完成("4", "手术完成"),
        取消手术登记("5", "取消手术登记"),
        手术审批未通过("6", "手术审批未通过");

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
