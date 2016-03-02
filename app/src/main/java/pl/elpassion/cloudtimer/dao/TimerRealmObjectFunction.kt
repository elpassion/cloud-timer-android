package pl.elpassion.cloudtimer.dao

import pl.elpassion.cloudtimer.domain.Group
import pl.elpassion.cloudtimer.domain.Timer


fun TimerRealmObject.createTimer(): Timer =
        Timer (
                uid = uuid,
                title = title,
                duration = duration,
                endTime = endTime,
                sync = sync,
                group = if (groupName != null && groupColor != null)
                    Group(groupName, groupColor)
                else null
        )