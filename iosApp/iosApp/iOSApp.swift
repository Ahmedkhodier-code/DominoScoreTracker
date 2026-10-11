import SwiftUI
import sharedUI

@main
struct iOSApp: App {
    init() {
        KoinHelper.shared.initKoinForIos()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
