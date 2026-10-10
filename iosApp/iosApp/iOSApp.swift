import SwiftUI
import SharedLogic

@main
struct iOSApp: App {
    init() {
        KoinInitKt.initKoin(additionalModules: [UiModuleKt.uiModule])
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}