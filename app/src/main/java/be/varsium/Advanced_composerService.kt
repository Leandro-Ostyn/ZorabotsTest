package be.varsium

import be.varsium.models.Advanced_composer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module

interface Advanced_composerService {

    fun Data():Advanced_composer
}

class  DataServiceImpl(private val advancedComposer: Advanced_composer): Advanced_composerService {
   override fun Data(): Advanced_composer {
        return advancedComposer
    }
}

class AdvancedComposerApplication : KoinComponent{
    private val advanced_composerService by inject<Advanced_composerService>()
    fun showData() = advanced_composerService.Data()
}
