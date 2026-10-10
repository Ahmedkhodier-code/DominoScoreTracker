import SwiftUI
import sharedUI

@main
struct iOSApp: App {
    init() {
        InitKoinUiKt.initKoinForIos()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
