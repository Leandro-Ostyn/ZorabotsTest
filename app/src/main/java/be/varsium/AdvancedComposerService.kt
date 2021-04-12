package be.varsium

import be.varsium.models.AdvancedComposer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface AdvancedComposerService {

    fun Data():AdvancedComposer
}

class  DataServiceImpl(private val advancedComposer: AdvancedComposer): AdvancedComposerService {
   override fun Data(): AdvancedComposer {
        return advancedComposer
    }
}
