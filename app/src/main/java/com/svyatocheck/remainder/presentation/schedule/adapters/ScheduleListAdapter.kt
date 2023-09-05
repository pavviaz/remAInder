//package com.application.feature_schedule.presentation.adapters
//
//import android.content.Context
//import android.content.res.ColorStateList
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.application.feature_schedule.R
//import com.application.feature_schedule.databinding.ListItemScheduleClassBinding
//import com.application.feature_schedule.databinding.ListItemScheduleClassPagerBinding
//import com.application.feature_schedule.databinding.ListItemScheduleHeaderBinding
//import com.application.feature_schedule.domain.models.ReceiverSubjectType
//import com.application.feature_schedule.domain.models.week.ScheduleDailyItem
//import com.application.feature_schedule.domain.models.week.ScheduleItemDomain
//import com.application.feature_schedule.domain.models.week.ScheduleItemType
//import com.google.android.material.tabs.TabLayoutMediator
//
//class ScheduleListAdapter(
//    private val scheduleList: ScheduleDailyItem,
//    private val bottomDialogListener: OnItemClickListener
//) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when (viewType) {
//            ScheduleItemType.HEADER.ordinal -> HeaderScheduleViewHolder(
//                ListItemScheduleHeaderBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent,
//                    false
//                )
//            )
//
//            ScheduleItemType.SUBGROUPS.ordinal -> SubgroupCardViewHolder(
//                ListItemScheduleClassPagerBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent,
//                    false
//                )
//            )
//
//            else -> SingleScheduleCardViewHolder(
//                ListItemScheduleClassBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent,
//                    false
//                )
//            )
//        }
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (getItemViewType(position)) {
//            ScheduleItemType.HEADER.ordinal -> (holder as HeaderScheduleViewHolder)
//                .bind(scheduleList.data[position])
//
//            ScheduleItemType.SUBGROUPS.ordinal -> (holder as SubgroupCardViewHolder)
//                .bind(scheduleList.data[position], bottomDialogListener)
//
//            else -> (holder as SingleScheduleCardViewHolder).scheduleCardBind(
//                scheduleList.data[position][0], bottomDialogListener
//            )
//
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return if (scheduleList.data[position].size >= 2) {
//            ScheduleItemType.SUBGROUPS.ordinal
//        } else {
//            return scheduleList.data[position][scheduleList.data[position].size - 1].classType.ordinal
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return scheduleList.data.size
//    }
//
//    class SubgroupCardViewHolder(private val binding: ListItemScheduleClassPagerBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(
//            list: List<ScheduleItemDomain>,
//            itemClickListener: OnItemClickListener
//        ) {
//            val adapter = SubgroupClassAdapter(list, itemClickListener)
//            binding.subgroupClassSchedule.adapter = adapter
//            binding.subgroupClassSchedule.setOnClickListener { itemClickListener.onItemClick(list) }
//            TabLayoutMediator(
//                binding.subgroupTabLayout,
//                binding.subgroupClassSchedule
//            ) { _, _ -> }.attach()
//        }
//    }
//
//    class SingleScheduleCardViewHolder(private val binding: ListItemScheduleClassBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun scheduleCardBind(
//            cardItem: ScheduleItemDomain,
//            itemClickListener: OnItemClickListener
//        ) {
//            val scheduleTypeTitle = defineTitleFunction(cardItem.classType.ordinal)
//            val scheduleTypeColor =
//                ColorStateList.valueOf(getItemColorAccent(scheduleTypeTitle, itemView.context))
//            val scheduleSubgroupColor =
//                ColorStateList.valueOf(getSubgroupItemColorAccent(scheduleTypeTitle, itemView.context))
//            with(binding) {
//
//                itemClassDecorator.backgroundTintList = scheduleTypeColor
//                itemClassType.backgroundTintList = scheduleTypeColor
//                itemClassType.text = itemView.resources.getString(scheduleTypeTitle)
//
//                itemClassSubgroup.backgroundTintList = scheduleSubgroupColor
//                itemClassSubgroup.setTextColor(scheduleTypeColor)
//
//                with(cardItem) {
//                    itemClassName.text = className
//                    fillCardField(itemClassStartTime, classStartTime)
//                    fillCardField(itemClassEndTime, classEndTime)
//                    fillCardField(itemClassOrderNumber, classNumber.toString())
//
//                    cardItem.receiverType.let {
//                        when (it) {
//                            ReceiverSubjectType.GROUP -> doGroupItemSetup(cardItem)
//                            ReceiverSubjectType.PROFESSOR -> doProfessorItemSetup(cardItem)
//                            ReceiverSubjectType.CLASSROOM -> doClassroomSetup(cardItem)
//                            else -> {}
//                        }
//                    }
//                    itemView.setOnClickListener {
//                        itemClickListener.onItemClick(listOf(cardItem))
//                    }
//                }
//            }
//        }
//
//        private fun doClassroomSetup(cardItem: ScheduleItemDomain) {
//            with(binding) {
//                with(cardItem) {
//                    classProfessor?.let { fillCardField(itemClassLocation, it) }
//                    classGroupName?.let { fillCardField(itemClassGroup, it) }
//                    createSubgroupLabel(
//                        itemClassSubgroup,
//                        classSubGroup,
//                        itemView.context
//                    )
//                    itemClassLocationImage.setImageResource(R.drawable.ic_graduation_cap)
//                }
//            }
//        }
//
//        private fun doProfessorItemSetup(cardItem: ScheduleItemDomain) {
//            with(binding) {
//                with(cardItem) {
//                    classPlace?.let { fillCardField(itemClassLocation, it) }
//                    classGroupName?.let { fillCardField(itemClassGroup, it) }
//                    createSubgroupLabel(
//                        itemClassSubgroup,
//                        classSubGroup,
//                        itemView.context
//                    )
//                }
//            }
//        }
//
//        private fun doGroupItemSetup(cardItem: ScheduleItemDomain) {
//            with(binding) {
//                with(cardItem) {
//                    classPlace?.let { fillCardField(itemClassLocation, it) }
//                    classProfessor?.let { fillCardField(itemClassGroup, it) }
//                    createSubgroupLabel(
//                        itemClassSubgroup,
//                        classSubGroup,
//                        itemView.context
//                    )
//                    itemClassGroupIcon.setImageResource(R.drawable.ic_graduation_cap)
//                }
//            }
//        }
//    }
//
//    class HeaderScheduleViewHolder(private val binding: ListItemScheduleHeaderBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(list: List<ScheduleItemDomain>) {
//            val cardItem = list[0]
//            with(binding) {
//                headerItemMessage.text =
//                    cardItem.className.ifBlank { itemView.resources.getString(R.string.schedule_card_error_title) }
//
//                if (cardItem.classEndTime.isNotBlank() || cardItem.classStartTime.isNotBlank()) {
//                    headerItemTime.text =
//                        "${cardItem.classStartTime} - ${cardItem.classEndTime}"
//                } else {
//                    headerItemTime.text =
//                        itemView.resources.getString(R.string.header_time_missing)
//                }
//            }
//        }
//
//    }
//
//    interface OnItemClickListener {
//        fun onItemClick(item: List<ScheduleItemDomain>)
//    }
//
//    companion object {
//
//        fun createSubgroupLabel(
//            subgroupName: TextView,
//            classSubgroup: Int?,
//            context: Context
//        ) {
//            if (classSubgroup == null || classSubgroup == 0) {
//                subgroupName.visibility = View.GONE
//                return
//            }
//            subgroupName.text = String.format(
//                context.resources.getString(R.string.schedule_subgroup_template),
//                classSubgroup
//            )
//
//
//        }
//
//        fun getItemColorAccent(itemTypeTitleRes: Int, context: Context): Int {
//            return context.resources.getColor(
//                when (itemTypeTitleRes) {
//                    R.string.schedule_card_practice_title -> {
//                        R.color.schedule_item_practice
//                    }
//
//                    R.string.schedule_card_lecture_title -> {
//                        R.color.schedule_item_lecture
//                    }
//
//                    R.string.schedule_card_exam_title -> {
//                        R.color.schedule_item_exam
//                    }
//
//                    else -> {
//                        R.color.schedule_item_credit
//                    }
//                }
//            )
//        }
//
//
//        fun getSubgroupItemColorAccent(itemTypeTitleRes: Int, context: Context): Int {
//            return context.resources.getColor(
//                when (itemTypeTitleRes) {
//                    R.string.schedule_card_practice_title -> {
//                        R.color.schedule_item_practice_subgroup
//                    }
//
//                    R.string.schedule_card_lecture_title -> {
//                        R.color.schedule_item_lecture_subgroup
//                    }
//
//                    R.string.schedule_card_exam_title -> {
//                        R.color.schedule_item_exam_subgroup
//                    }
//
//                    else -> {
//                        R.color.schedule_item_credit_subgroup
//                    }
//                }
//            )
//        }
//
//        fun defineTitleFunction(itemType: Int): Int {
//            return when (itemType) {
//                ScheduleItemType.LECTURE.ordinal -> R.string.schedule_card_lecture_title
//                ScheduleItemType.PRACTICE.ordinal -> R.string.schedule_card_practice_title
//                ScheduleItemType.FACULTATIVE.ordinal -> R.string.schedule_card_facultative_title
//                ScheduleItemType.CREDIT.ordinal -> R.string.schedule_card_credit_title
//                ScheduleItemType.EXAM.ordinal -> R.string.schedule_card_exam_title
//                else -> R.string.schedule_card_error_title
//            }
//        }
//
//        fun fillCardField(itemClassField: TextView, fieldData: String) {
//            if (fieldData.isEmpty()) {
//                itemClassField.visibility = View.GONE
//            } else {
//                itemClassField.text = fieldData
//            }
//        }
//
//    }
//}