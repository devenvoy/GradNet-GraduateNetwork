//
//  Helper.swift
//  iosApp
//
//  Created by Devansh pc on 08/03/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import UIKit
import MessageUI

@objc public class IosHelper : NSObject{
    
    @objc public func showAlert(title: String, message: String) {
        if let topController = UIApplication.shared
            .connectedScenes
            .compactMap({ $0 as? UIWindowScene })
            .flatMap({ $0.windows })
            .first(where: { $0.isKeyWindow })?
            .rootViewController {
            
            let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
            
            DispatchQueue.main.async {
                topController.present(alert, animated: true, completion: nil)
            }
        }
    }
    
    @objc public func openLink(link: String) {
        if let url = URL(string: link), UIApplication.shared.canOpenURL(url) {
            UIApplication.shared.open(url,
                                      options: [:],
                                      completionHandler: nil)
        }
    }
    
    @objc public func sendSms(phoneNumber: String, message: String) {
        let sms = "sms:\(phoneNumber)?body=\(message)"
        if let smsUrl  = sms.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed),
           let url = URL(string: smsUrl) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        }else{
            print("Invalid sms url \(sms)")
        }
    }
    
    @objc public func dialPhoneNumber(phoneNumber: String) {
          if let url = URL(string: "tel://\(phoneNumber)"), UIApplication.shared.canOpenURL(url) {
              UIApplication.shared.open(url, options: [:], completionHandler: nil)
          }
      }
      
      @objc public func sendEmail(email: String, subject: String, body: String) {
          let emailString = "mailto:\(email)?subject=\(subject.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed) ?? "")&body=\(body.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed) ?? "")"
          
          if let emailURL = URL(string: emailString), UIApplication.shared.canOpenURL(emailURL) {
              UIApplication.shared.open(emailURL, options: [:], completionHandler: nil)
          } else {
              print("Cannot open email client")
          }
      }
      
    @objc public func shareText(text: String) {
        guard let from = getCurrentUIViewController() else { return }
        let activityVC = UIActivityViewController(activityItems: [text], applicationActivities: nil)
        from.present(activityVC, animated: true, completion: nil)
    }

    @objc public func sharePost(imageUrls: [String], description: String) {
        guard let from = getCurrentUIViewController() else { return }
        var images: [UIImage] = []
        let dispatchGroup = DispatchGroup()

        for urlString in imageUrls {
            dispatchGroup.enter()
            if let url = URL(string: urlString) {
                URLSession.shared.dataTask(with: url) { data, _, _ in
                    if let data = data, let image = UIImage(data: data) {
                        images.append(image)
                    }
                    dispatchGroup.leave()
                }.resume()
            } else {
                dispatchGroup.leave()
            }
        }

        dispatchGroup.notify(queue: .main) {
            if images.isEmpty && description.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty {
                let alert = UIAlertController(title: "Nothing to Share", message: "Please add content before sharing.", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "OK", style: .default))
                from.present(alert, animated: true)
                return
            }

            var activityItems: [Any] = []
            if !images.isEmpty { activityItems.append(contentsOf: images) }
            if !description.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty {
                activityItems.append("\(description)\n\nShared via GradNet")
            }

            let activityVC = UIActivityViewController(activityItems: activityItems, applicationActivities: nil)
            activityVC.excludedActivityTypes = [.assignToContact, .addToReadingList]

            from.present(activityVC, animated: true, completion: nil)
        }
    }
    
    func getCurrentUIViewController() -> UIViewController? {
        return UIApplication.shared.keyWindow?.rootViewController
    }
}
